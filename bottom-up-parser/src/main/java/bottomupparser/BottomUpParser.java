package bottomupparser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import simplelexersubset.SimpleLexer;
import simplelexersubset.Token;

/**
 * A table-driven bottom-up parser that consumes tokens from {@link SimpleLexer}.
 *
 * <p>Select {@link ParserType#SLR1}, {@link ParserType#LR1}, or {@link ParserType#LALR1} in
 * the constructor. The grammar-terminal convention is the same as the top-down parser: use
 * {@code IF}, {@code ID}, and {@code INT} for word-like lexer token kinds and literal
 * {@code (}, {@code )}, {@code +}, {@code *}, and {@code =} for punctuation.
 */
public final class BottomUpParser {
  private static final int MAX_PARSE_STEPS = 100000;

  private final Grammar grammar;
  private final LrParsingTable table;

  /** Constructs an SLR(1) parser. Prefer the other constructor when configuration matters. */
  public BottomUpParser(final Grammar grammar) {
    this(grammar, ParserType.SLR1);
  }

  /** Constructs a parser and its complete parsing table using {@code parserType}. */
  public BottomUpParser(final Grammar grammar, final ParserType parserType) {
    if (grammar == null) {
      throw new IllegalArgumentException("grammar must not be null");
    }
    if (parserType == null) {
      throw new IllegalArgumentException("parserType must not be null");
    }
    this.grammar = grammar;
    this.table = LrParsingTable.build(grammar, parserType);
  }

  /** Reads a grammar file and constructs an SLR(1) parser. */
  public static BottomUpParser fromJson(final Path grammarFile) throws IOException {
    return new BottomUpParser(Grammar.fromJson(grammarFile));
  }

  /** Reads a grammar file and builds the selected kind of bottom-up parser. */
  public static BottomUpParser fromJson(final Path grammarFile, final ParserType parserType)
      throws IOException {
    return new BottomUpParser(Grammar.fromJson(grammarFile), parserType);
  }

  public Grammar getGrammar() {
    return grammar;
  }

  /** The table-construction algorithm selected for this parser. */
  public ParserType getParserType() {
    return table.getParserType();
  }

  public LrParsingTable getTable() {
    return table;
  }

  /** Returns whether the supplied source text is accepted by this parser's table. */
  public boolean parses(final String source) {
    return parse(source).isSuccess();
  }

  /**
   * Parses one source string.
   *
   * <p>A conflict only rejects an input when its current state and lookahead reach the
   * conflicting ACTION cell. This retains useful parsing behavior for an unambiguous input
   * while never resolving a shift/reduce or reduce/reduce conflict arbitrarily.
   */
  public ParseResult parse(final String source) {
    if (source == null) {
      throw new IllegalArgumentException("source must not be null");
    }
    final Deque<Integer> states = new ArrayDeque<Integer>();
    states.push(Integer.valueOf(0));
    final SimpleLexer lexer = new SimpleLexer(source);
    Token lookahead;
    try {
      lookahead = lexer.nextToken();
    } catch (final SimpleLexer.LexicalException exception) {
      return ParseResult.failure(exception.getMessage());
    }

    for (int steps = 0; steps < MAX_PARSE_STEPS; steps++) {
      final int state = states.peek().intValue();
      final String terminal = terminalFor(lookahead);
      final List<LrAction> candidates = table.getActions(state, terminal);
      if (candidates.isEmpty()) {
        return ParseResult.failure("No ACTION entry for [" + state + ", " + terminal + "]");
      }
      if (candidates.size() > 1) {
        return ParseResult.failure("" + table.getParserType().getDisplayName()
            + " conflict in ACTION[" + state + ", " + terminal + "]: " + candidates);
      }
      final LrAction action = candidates.get(0);
      switch (action.getKind()) {
        case SHIFT:
          states.push(Integer.valueOf(action.getNextState()));
          try {
            lookahead = lexer.nextToken();
          } catch (final SimpleLexer.LexicalException exception) {
            return ParseResult.failure(exception.getMessage());
          }
          break;
        case REDUCE:
          final Production production = action.getProduction();
          final int symbolsToPop = production.getRightHandSide().size();
          if (states.size() <= symbolsToPop) {
            return ParseResult.failure("Invalid reduction by " + production);
          }
          for (int i = 0; i < symbolsToPop; i++) {
            states.pop();
          }
          final Integer target = table.getGoto(states.peek().intValue(),
              production.getLeftHandSide());
          if (target == null) {
            return ParseResult.failure("No GOTO entry for [" + states.peek() + ", "
                + production.getLeftHandSide() + "] after reducing by " + production);
          }
          states.push(target);
          break;
        case ACCEPT:
          return ParseResult.success();
        default:
          throw new AssertionError(action.getKind());
      }
    }
    return ParseResult.failure("Parser exceeded " + MAX_PARSE_STEPS
        + " steps; grammar may contain a non-progressing recursion");
  }

  /** Prints a complete parsing table. Usage: {@code BottomUpParser <type> <grammar.json>}. */
  public static void main(final String[] arguments) throws IOException {
    if (arguments.length != 2) {
      throw new IllegalArgumentException("Usage: BottomUpParser <SLR(1)|LR(1)|LALR(1)>"
          + " <grammar.json>");
    }
    final BottomUpParser parser = fromJson(Paths.get(arguments[1]),
        ParserType.fromName(arguments[0]));
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
      case EQ:
        return "=";
      default:
        return token.getType().name();
    }
  }
}
