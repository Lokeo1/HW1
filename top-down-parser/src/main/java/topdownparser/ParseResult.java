package topdownparser;

/** The outcome of parsing one input string with a predictive parsing table. */
public final class ParseResult {
  private static final ParseResult SUCCESS = new ParseResult(true, "Input accepted");

  private final boolean success;
  private final String message;

  private ParseResult(final boolean success, final String message) {
    this.success = success;
    this.message = message;
  }

  static ParseResult success() {
    return SUCCESS;
  }

  static ParseResult failure(final String message) {
    return new ParseResult(false, message);
  }

  public boolean isSuccess() {
    return success;
  }

  /** A short explanation of acceptance or rejection. */
  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return (success ? "success: " : "failure: ") + message;
  }
}
