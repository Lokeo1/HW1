package topdownparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An LL(1) predictive parsing table.
 *
 * <p>Every applicable production is retained in a cell. Consequently, a cell containing more
 * than one production explicitly records an LL(1) conflict instead of silently choosing one.
 */
public final class Ll1ParsingTable {
  private final Grammar grammar;
  private final Map<String, Set<String>> firstSets;
  private final Map<String, Set<String>> followSets;
  private final Map<String, Map<String, List<Production>>> cells;
  private final List<Conflict> conflicts;
  private final List<String> columns;

  private Ll1ParsingTable(final Grammar grammar, final Map<String, Set<String>> firstSets,
      final Map<String, Set<String>> followSets,
      final Map<String, Map<String, List<Production>>> cells) {
    this.grammar = grammar;
    this.firstSets = immutableSetMap(firstSets);
    this.followSets = immutableSetMap(followSets);
    this.cells = immutableCells(cells);
    this.columns = new ArrayList<String>(grammar.getTerminals());
    this.columns.add(Grammar.END_MARKER);
    this.conflicts = findConflicts(this.cells);
  }

  /** Constructs the complete table, including conflicting cells when they exist. */
  public static Ll1ParsingTable build(final Grammar grammar) {
    // TODO: Complete this method
    return null; // TODO: replace the return value.
  }

  public Grammar getGrammar() {
    return grammar;
  }

  /** FIRST sets keyed by nonterminal. Epsilon is represented by {@link Grammar#EPSILON}. */
  public Map<String, Set<String>> getFirstSets() {
    return firstSets;
  }

  /** FOLLOW sets keyed by nonterminal. */
  public Map<String, Set<String>> getFollowSets() {
    return followSets;
  }

  public List<String> getColumns() {
    return Collections.unmodifiableList(columns);
  }

  /** Returns every production in a table cell, or an empty list for an empty cell. */
  public List<Production> getCell(final String nonterminal, final String terminal) {
    final Map<String, List<Production>> row = cells.get(nonterminal);
    if (row == null) {
      throw new IllegalArgumentException("Unknown nonterminal '" + nonterminal + "'");
    }
    final List<Production> result = row.get(terminal);
    return result == null ? Collections.<Production>emptyList() : result;
  }

  /** Returns true when every table cell contains at most one production. */
  public boolean isLl1() {
    return conflicts.isEmpty();
  }

  /** Returns all multi-production cells that make the grammar non-LL(1). */
  public List<Conflict> getConflicts() {
    return conflicts;
  }

  /** Formats the complete parsing table, retaining every conflicting production. */
  public String format() {
    final List<String> rows = new ArrayList<String>(grammar.getNonterminals());
    final List<Integer> widths = new ArrayList<Integer>();
    widths.add("Nonterminal".length());
    for (final String column : columns) {
      widths.add(column.length());
    }
    for (final String row : rows) {
      widths.set(0, Math.max(widths.get(0), row.length()));
      for (int i = 0; i < columns.size(); i++) {
        widths.set(i + 1, Math.max(widths.get(i + 1),
            formatCell(getCell(row, columns.get(i))).length()));
      }
    }

    final StringBuilder result = new StringBuilder();
    appendRow(result, widths, "Nonterminal", columns);
    appendSeparator(result, widths);
    for (final String row : rows) {
      final List<String> values = new ArrayList<String>();
      for (final String column : columns) {
        values.add(formatCell(getCell(row, column)));
      }
      appendRow(result, widths, row, values);
    }
    return result.toString();
  }

  @Override
  public String toString() {
    return format();
  }

  private static Map<String, Set<String>> initialSets(final Grammar grammar) {
    final Map<String, Set<String>> result = new LinkedHashMap<String, Set<String>>();
    for (final String nonterminal : grammar.getNonterminals()) {
      result.put(nonterminal, new LinkedHashSet<String>());
    }
    return result;
  }

  private static Set<String> firstOfSequence(final List<String> symbols, final Grammar grammar,
      final Map<String, Set<String>> first) {
    final Set<String> result = new LinkedHashSet<String>();
    if (symbols.isEmpty()) {
      result.add(Grammar.EPSILON);
      return result;
    }
    for (final String symbol : symbols) {
      if (!grammar.getNonterminals().contains(symbol)) {
        result.add(symbol);
        return result;
      }
      final Set<String> symbolFirst = first.get(symbol);
      addWithoutEpsilon(result, symbolFirst);
      if (!symbolFirst.contains(Grammar.EPSILON)) {
        return result;
      }
    }
    result.add(Grammar.EPSILON);
    return result;
  }

  private static boolean addWithoutEpsilon(final Set<String> target, final Set<String> source) {
    boolean changed = false;
    for (final String symbol : source) {
      if (!Grammar.EPSILON.equals(symbol)) {
        changed |= target.add(symbol);
      }
    }
    return changed;
  }

  private static void add(final Map<String, Map<String, List<Production>>> cells,
      final String nonterminal, final String terminal, final Production production) {
    final Map<String, List<Production>> row = cells.get(nonterminal);
    List<Production> cell = row.get(terminal);
    if (cell == null) {
      cell = new ArrayList<Production>();
      row.put(terminal, cell);
    }
    cell.add(production);
  }

  private static Map<String, Set<String>> immutableSetMap(final Map<String, Set<String>> source) {
    final Map<String, Set<String>> result = new LinkedHashMap<String, Set<String>>();
    for (final Map.Entry<String, Set<String>> entry : source.entrySet()) {
      result.put(entry.getKey(), Collections.unmodifiableSet(
          new LinkedHashSet<String>(entry.getValue())));
    }
    return Collections.unmodifiableMap(result);
  }

  private static Map<String, Map<String, List<Production>>> immutableCells(
      final Map<String, Map<String, List<Production>>> source) {
    final Map<String, Map<String, List<Production>>> result =
        new LinkedHashMap<String, Map<String, List<Production>>>();
    for (final Map.Entry<String, Map<String, List<Production>>> row : source.entrySet()) {
      final Map<String, List<Production>> copiedRow =
          new LinkedHashMap<String, List<Production>>();
      for (final Map.Entry<String, List<Production>> cell : row.getValue().entrySet()) {
        copiedRow.put(cell.getKey(), Collections.unmodifiableList(
            new ArrayList<Production>(cell.getValue())));
      }
      result.put(row.getKey(), Collections.unmodifiableMap(copiedRow));
    }
    return Collections.unmodifiableMap(result);
  }

  private static List<Conflict> findConflicts(
      final Map<String, Map<String, List<Production>>> cells) {
    final List<Conflict> result = new ArrayList<Conflict>();
    for (final Map.Entry<String, Map<String, List<Production>>> row : cells.entrySet()) {
      for (final Map.Entry<String, List<Production>> cell : row.getValue().entrySet()) {
        if (cell.getValue().size() > 1) {
          result.add(new Conflict(row.getKey(), cell.getKey(), cell.getValue()));
        }
      }
    }
    return Collections.unmodifiableList(result);
  }

  private static String formatCell(final List<Production> productions) {
    if (productions.isEmpty()) {
      return "";
    }
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < productions.size(); i++) {
      if (i > 0) {
        result.append(" ,  ");
      }
      result.append(productions.get(i));
    }
    return result.toString();
  }

  private static void appendRow(final StringBuilder output, final List<Integer> widths,
      final String heading, final List<String> values) {
    appendPadded(output, heading, widths.get(0));
    for (int i = 0; i < values.size(); i++) {
      output.append(" | ");
      appendPadded(output, values.get(i), widths.get(i + 1));
    }
    output.append(System.lineSeparator());
  }

  private static void appendSeparator(final StringBuilder output, final List<Integer> widths) {
    for (int i = 0; i < widths.size(); i++) {
      if (i > 0) {
        output.append("-+-");
      }
      for (int j = 0; j < widths.get(i); j++) {
        output.append('-');
      }
    }
    output.append(System.lineSeparator());
  }

  private static void appendPadded(final StringBuilder output, final String value, final int width) {
    output.append(value);
    for (int i = value.length(); i < width; i++) {
      output.append(' ');
    }
  }

  /** A parsing-table cell that contains more than one applicable production. */
  public static final class Conflict {
    private final String nonterminal;
    private final String terminal;
    private final List<Production> productions;

    private Conflict(final String nonterminal, final String terminal,
        final List<Production> productions) {
      this.nonterminal = nonterminal;
      this.terminal = terminal;
      this.productions = Collections.unmodifiableList(new ArrayList<Production>(productions));
    }

    public String getNonterminal() {
      return nonterminal;
    }

    public String getTerminal() {
      return terminal;
    }

    public List<Production> getProductions() {
      return productions;
    }

    @Override
    public String toString() {
      return "M[" + nonterminal + ", " + terminal + "] = " + formatCell(productions);
    }
  }
}
