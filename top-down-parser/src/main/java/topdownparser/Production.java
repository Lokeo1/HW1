package topdownparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** An immutable context-free grammar production. */
public final class Production {
  private final int number;
  private final String leftHandSide;
  private final List<String> rightHandSide;

  Production(final int number, final String leftHandSide, final List<String> rightHandSide) {
    this.number = number;
    this.leftHandSide = leftHandSide;
    this.rightHandSide = Collections.unmodifiableList(new ArrayList<String>(rightHandSide));
  }

  /** The zero-based position of this production in the grammar file. */
  public int getNumber() {
    return number;
  }

  public String getLeftHandSide() {
    return leftHandSide;
  }

  /**
   * Returns the symbols on the right-hand side. An empty list represents epsilon.
   */
  public List<String> getRightHandSide() {
    return rightHandSide;
  }

  @Override
  public String toString() {
    return leftHandSide + " -> "
        + (rightHandSide.isEmpty() ? Grammar.EPSILON : join(rightHandSide));
  }

  private static String join(final List<String> symbols) {
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < symbols.size(); i++) {
      if (i > 0) {
        result.append(' ');
      }
      result.append(symbols.get(i));
    }
    return result.toString();
  }
}
