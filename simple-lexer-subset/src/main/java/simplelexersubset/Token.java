package simplelexersubset;

/** A token produced by {@link SimpleLexer}. */
public final class Token {
  public enum Type {
    IF,
    INT,
    ID,
    EOF
  }

  private final Type type;
  private final String text;
  private final int start;

  /**
   * Constructs a new token with the given type, text, and start position.
   *
   * @param type the type of the token
   * @param text the text of the token
   * @param start the zero-based character offset of the first character in this token
   */
  public Token(final Type type, final String text, final int start) {
    this.type = type;
    this.text = text;
    this.start = start;
  }

  public Type getType() {
    return this.type;
  }

  public String getText() {
    return this.text;
  }

  /** Zero-based character offset of the first character in this token. */
  public int getStart() {
    return this.start;
  }

  @Override
  public String toString() {
    return this.type + "(" + this.text + ")";
  }
}
