package simplelexer;

/**
 * A standalone lexer for the {@code if} keyword, integer literals, and identifiers.
 *
 * <p>The DFA has these accepting states:
 * <ul>
 *   <li>{@code ZERO}: the integer {@code 0}</li>
 *   <li>{@code INT}: a nonzero digit followed by zero or more digits</li>
 *   <li>{@code ID}: an identifier matching {@code [A-Za-z_][A-Za-z0-9_]*}</li>
 * </ul>
 * Whitespace is skipped. Any other character causes a {@link LexicalException}.
 */
public final class SimpleLexer {
  private enum State {
    START, ZERO, INT, ID
  }

  private final String source;
  private int position;

  public SimpleLexer(final String source) {
    if (source == null) {
      throw new IllegalArgumentException("source must not be null");
    }
    this.source = source;
  }

  /** Returns the next token, or {@link Token.Type#EOF} after the input ends. */
  public Token nextToken() {
    this.skipWhitespace();
    if (this.position == this.source.length()) {
      return new Token(Token.Type.EOF, "", this.position);
    }

    final int start = this.position;
    State state = State.START;

    while (this.position < this.source.length()) {
      final char c = this.source.charAt(this.position);
      final State next = this.transition(state, c);
      if (next == null) {
        break;
      }
      state = next;
      this.position++;
    }

    if (state == State.ZERO || state == State.INT) {
      return new Token(Token.Type.INT, this.source.substring(start, this.position),
          start);
    }
    if (state == State.ID) {
      final String text = this.source.substring(start, this.position);
      final Token.Type type = "if".equals(text) ? Token.Type.IF : Token.Type.ID;
      return new Token(type, text, start);
    }
    throw new LexicalException(this.source.charAt(this.position), this.position);
  }

  /** DFA transition function. A null result means that no transition exists. */
  private State transition(final State state, final char c) {
    switch (state) {
      // TODO: Implement the DFA transitions.      
      case START:
        if (c == '0') {
            return State.ZERO;
        }
        if(isIdentifierStart(c)){
          return State.ID;
        }
        if(isDigit(c)) {
          return State.INT;
        }
        return null;
      case ZERO:
        return null;
      case INT:
        if(isDigit(c)){
          return State.INT;
        }
        return null;
      case ID:
        if(isIdentifierPart(c)){
          return State.ID;
        }
        return null;
      default:
        throw new AssertionError("Unknown DFA state: " + state);
    }
  }

  private void skipWhitespace() {
    while (this.position < this.source.length()
        && Character.isWhitespace(this.source.charAt(this.position))) {
      this.position++;
    }
  }

  private static boolean isDigit(final char c) {
    return c >= '0' && c <= '9';
  }

  private static boolean isIdentifierStart(final char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
        || c == '_';
  }

  private static boolean isIdentifierPart(final char c) {
    return isIdentifierStart(c) || isDigit(c);
  }

  public static final class LexicalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LexicalException(final char character, final int position) {
      super("Unexpected character '" + character + "' at offset " + position);
    }
  }
}
