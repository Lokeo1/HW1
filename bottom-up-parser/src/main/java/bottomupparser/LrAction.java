package bottomupparser;

/** An action in an LR ACTION table cell. */
public final class LrAction {
  /** The three action kinds in an LR parsing table. */
  public enum Kind {
    SHIFT,
    REDUCE,
    ACCEPT
  }

  private final Kind kind;
  private final int nextState;
  private final Production production;

  private LrAction(final Kind kind, final int nextState, final Production production) {
    this.kind = kind;
    this.nextState = nextState;
    this.production = production;
  }

  static LrAction shift(final int nextState) {
    return new LrAction(Kind.SHIFT, nextState, null);
  }

  static LrAction reduce(final Production production) {
    return new LrAction(Kind.REDUCE, -1, production);
  }

  static LrAction accept() {
    return new LrAction(Kind.ACCEPT, -1, null);
  }

  public Kind getKind() {
    return kind;
  }

  /** The target state of a SHIFT action. Only valid for {@link Kind#SHIFT}. */
  public int getNextState() {
    if (kind != Kind.SHIFT) {
      throw new IllegalStateException("Only a SHIFT action has a target state");
    }
    return nextState;
  }

  /** The production of a REDUCE action. Only valid for {@link Kind#REDUCE}. */
  public Production getProduction() {
    if (kind != Kind.REDUCE) {
      throw new IllegalStateException("Only a REDUCE action has a production");
    }
    return production;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof LrAction)) {
      return false;
    }
    final LrAction that = (LrAction) other;
    if (kind != that.kind || nextState != that.nextState) {
      return false;
    }
    return production == null ? that.production == null
        : that.production != null && production.getNumber() == that.production.getNumber();
  }

  @Override
  public int hashCode() {
    return 31 * (31 * kind.hashCode() + nextState)
        + (production == null ? 0 : production.getNumber());
  }

  @Override
  public String toString() {
    switch (kind) {
      case SHIFT:
        return "s" + nextState;
      case REDUCE:
        return "r" + production.getNumber() + " (" + production + ")";
      case ACCEPT:
        return "acc";
      default:
        throw new AssertionError(kind);
    }
  }
}
