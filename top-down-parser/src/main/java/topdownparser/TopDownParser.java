package topdownparser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import simplelexersubset.SimpleLexer;
import simplelexersubset.Token;

/**
 * Table-driven top-down parser that consumes tokens from {@link SimpleLexer}.
 *
 * <p>Grammar terminals use the lexer's token-kind names {@code IF}, {@code ID}, and
 * {@code INT}; the punctuation tokens use their literal grammar symbols {@code (}, {@code )},
 * {@code +}, and {@code *}. The lexer produces the table's {@code $} end marker after its final
 * token.
 */
public final class TopDownParser {
  private static final int MAX_PARSE_STEPS = 100000;

  private final Grammar grammar;
  private final Ll1ParsingTable table;

  public TopDownParser(final Grammar grammar) {
    if (grammar == null) {
      throw new IllegalArgumentException("grammar must not be null");
    }
    this.grammar = grammar;
    this.table = Ll1ParsingTable.build(grammar);
  }

  /** Reads a grammar file and builds its LL(1) parsing table. */
  public static TopDownParser fromJson(final Path grammarFile) throws IOException {
    return new TopDownParser(Grammar.fromJson(grammarFile));
  }

  public Grammar getGrammar() {
    return grammar;
  }

  public Ll1ParsingTable getTable() {
    return table;
  }

  /** Returns whether the supplied source text is accepted by this parser's table. */
  public boolean parses(final String source) {
    return parse(source).isSuccess();
  }

  /**
   * Parses the supplied source text.
   *
   * <p>Parsing fails at a conflicting table cell; this ensures a non-LL(1) grammar is never
   * accepted by arbitrarily choosing one of its possible productions.
   */
  public ParseResult parse(final String source) {
    if (source == null) {
      throw new IllegalArgumentException("source must not be null");
    }
    final Deque<String> stack = new ArrayDeque<String>();
    stack.push(Grammar.END_MARKER);
    stack.push(grammar.getStartSymbol());
    final SimpleLexer lexer = new SimpleLexer(source);
    Token lookahead;
    try {
      lookahead = lexer.nextToken();
    } catch (final SimpleLexer.LexicalException exception) {
      return ParseResult.failure(exception.getMessage());
    }

    for (int steps = 0; steps < MAX_PARSE_STEPS; steps++) {
      final String top = stack.pop();
      final String lookaheadTerminal = terminalFor(lookahead);
      if (Grammar.END_MARKER.equals(top)) {
        return Grammar.END_MARKER.equals(lookaheadTerminal)
            ? ParseResult.success()
            : ParseResult.failure("Unexpected token " + lookahead + " after a complete parse");
      }
      if (grammar.getTerminals().contains(top)) {
        if (!top.equals(lookaheadTerminal)) {
          return ParseResult.failure("Expected " + top + " but found " + lookahead);
        }
        try {
          lookahead = lexer.nextToken();
        } catch (final SimpleLexer.LexicalException exception) {
          return ParseResult.failure(exception.getMessage());
        }
        continue;
      }

      final List<Production> candidates = table.getCell(top, lookaheadTerminal);
      if (candidates.isEmpty()) {
        return ParseResult.failure("No table entry for M[" + top + ", " + lookaheadTerminal
            + "]");
      }
      if (candidates.size() > 1) {
        return ParseResult.failure("LL(1) conflict in M[" + top + ", " + lookaheadTerminal
            + "]: " + candidates);
      }
      final List<String> rightHandSide = candidates.get(0).getRightHandSide();
      for (int i = rightHandSide.size() - 1; i >= 0; i--) {
        stack.push(rightHandSide.get(i));
      }
    }
    return ParseResult.failure("Parser exceeded " + MAX_PARSE_STEPS
        + " steps; grammar may contain a non-progressing recursion");
  }

  /** Prints a grammar file's complete parsing table. */
  public static void main(final String[] arguments) throws IOException {
    if (arguments.length != 1) {
      throw new IllegalArgumentException("Usage: TopDownParser <grammar.json>");
    }
    final TopDownParser parser = fromJson(Paths.get(arguments[0]));
    System.out.print(parser.getTable().format());
  }

  private static String terminalFor(final Token token) {
    switch (token.getType()) {
      case EOF:
        return Grammar.END_MARKER;
      case LPAREN:
        return "(";
      case RPAREN:
        return ")";
      case PLUS:
        return "+";
      case STAR:
        return "*";
      default:
        return token.getType().name();
    }
  }

}
