package bottomupparser;

/** The bottom-up table-construction algorithm to use. */
public enum ParserType {
  /** Simple LR: LR(0) item sets with FOLLOW-set reductions. */
  SLR1("SLR(1)"),
  /** Canonical LR: LR(1) item sets and lookaheads. */
  LR1("LR(1)"),
  /** Lookahead LR: canonical LR(1) states merged by their LR(0) cores. */
  LALR1("LALR(1)");

  private final String displayName;

  ParserType(final String displayName) {
    this.displayName = displayName;
  }

  /** A conventional name suitable for a table heading. */
  public String getDisplayName() {
    return displayName;
  }

  /** Parses either an enum name (for example {@code LALR1}) or {@code LALR(1)}. */
  public static ParserType fromName(final String name) {
    if (name == null) {
      throw new IllegalArgumentException("parser type must not be null");
    }
    final String normalized = name.toUpperCase().replace("(", "").replace(")", "")
        .replace("-", "").replace("_", "");
    if ("SLR".equals(normalized) || "SLR1".equals(normalized)) {
      return SLR1;
    }
    if ("LR".equals(normalized) || "LR1".equals(normalized)) {
      return LR1;
    }
    if ("LALR".equals(normalized) || "LALR1".equals(normalized)) {
      return LALR1;
    }
    throw new IllegalArgumentException("Unknown parser type '" + name
        + "'; expected SLR(1), LR(1), or LALR(1)");
  }
}
