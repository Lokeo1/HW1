package bottomupparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A complete SLR(1), LR(1), or LALR(1) parsing table.
 *
 * <p>All actions are kept in a table cell. Thus no conflict is hidden by an arbitrary
 * shift-or-reduce policy: a conflicting cell has two or more actions and is reported through
 * {@link #getConflicts()} as well as in {@link #format()}.
 */
public final class LrParsingTable {
  private final Grammar grammar;
  private final ParserType parserType;
  private final Map<String, Set<String>> firstSets;
  private final Map<String, Set<String>> followSets;
  private final List<Set<Item>> states;
  private final List<Map<String, List<LrAction>>> actions;
  private final List<Map<String, Integer>> gotos;
  private final List<String> actionColumns;
  private final List<String> gotoColumns;
  private final List<Conflict> conflicts;

  private LrParsingTable(final Grammar grammar, final ParserType parserType,
      final GrammarAnalysis analysis, final Automaton automaton, final Production augmented) {
    this.grammar = grammar;
    this.parserType = parserType;
    this.firstSets = analysis.getFirstSets();
    this.followSets = analysis.getFollowSets();
    this.states = immutableStates(automaton.states);
    this.actionColumns = new ArrayList<String>(grammar.getTerminals());
    this.actionColumns.add(Grammar.END_MARKER);
    this.gotoColumns = new ArrayList<String>(grammar.getNonterminals());

    final List<Map<String, List<LrAction>>> mutableActions = emptyActionRows(states.size());
    final List<Map<String, Integer>> mutableGotos = emptyGotoRows(states.size());
    populateGotos(mutableGotos, automaton.transitions, grammar);
    populateActions(mutableActions, automaton.transitions, grammar, parserType, analysis, augmented,
        states);
    this.actions = immutableActionRows(mutableActions);
    this.gotos = immutableGotoRows(mutableGotos);
    this.conflicts = findConflicts(this.actions);
  }

  /** Builds a table with the selected bottom-up parser construction. */
  public static LrParsingTable build(final Grammar grammar, final ParserType parserType) {
    if (grammar == null) {
      throw new IllegalArgumentException("grammar must not be null");
    }
    if (parserType == null) {
      throw new IllegalArgumentException("parserType must not be null");
    }
    final GrammarAnalysis analysis = GrammarAnalysis.analyze(grammar);
    final Production augmented = new Production(-1, augmentedStart(grammar),
        Collections.singletonList(grammar.getStartSymbol()));
    final Automaton automaton;
    switch (parserType) {
      case SLR1:
        automaton = buildLR0Automaton(grammar, augmented);
        break;
      case LR1:
        automaton = buildCanonicalLR1Automaton(grammar, analysis, augmented);
        break;
      case LALR1:
        automaton = mergeLALRStates(buildCanonicalLR1Automaton(grammar, analysis, augmented));
        break;
      default:
        throw new AssertionError(parserType);
    }
    return new LrParsingTable(grammar, parserType, analysis, automaton, augmented);
  }

  public Grammar getGrammar() {
    return grammar;
  }

  public ParserType getParserType() {
    return parserType;
  }

  /** FIRST sets keyed by nonterminal. Epsilon is represented by {@link Grammar#EPSILON}. */
  public Map<String, Set<String>> getFirstSets() {
    return firstSets;
  }

  /** FOLLOW sets keyed by nonterminal. */
  public Map<String, Set<String>> getFollowSets() {
    return followSets;
  }

  /** Number of item sets, and therefore number of table rows. */
  public int getStateCount() {
    return states.size();
  }

  /** The terminal columns of the ACTION portion of the table, including {@code $}. */
  public List<String> getActionColumns() {
    return Collections.unmodifiableList(actionColumns);
  }

  /** The nonterminal columns of the GOTO portion of the table. */
  public List<String> getGotoColumns() {
    return Collections.unmodifiableList(gotoColumns);
  }

  /**
   * Returns all actions in ACTION[state, terminal]. An empty list denotes an error entry.
   */
  public List<LrAction> getActions(final int state, final String terminal) {
    checkState(state);
    final List<LrAction> result = actions.get(state).get(terminal);
    return result == null ? Collections.<LrAction>emptyList() : result;
  }

  /** Alias for {@link #getActions(int, String)} useful when reading LR table notation. */
  public List<LrAction> getAction(final int state, final String terminal) {
    return getActions(state, terminal);
  }

  /** Returns GOTO[state, nonterminal], or {@code null} when the cell is empty. */
  public Integer getGoto(final int state, final String nonterminal) {
    checkState(state);
    return gotos.get(state).get(nonterminal);
  }

  /**
   * A printable item set. An LR(1) item ends in its lookahead after a comma; SLR items do not.
   */
  public List<String> getItems(final int state) {
    checkState(state);
    final List<String> result = new ArrayList<String>();
    for (final Item item : states.get(state)) {
      result.add(item.toString());
    }
    return Collections.unmodifiableList(result);
  }

  /** Returns true exactly when every ACTION cell has at most one action. */
  public boolean isConflictFree() {
    return conflicts.isEmpty();
  }

  /** Returns all shift/reduce, reduce/reduce, or other ACTION-cell conflicts. */
  public List<Conflict> getConflicts() {
    return conflicts;
  }

  /** Formats the ACTION and GOTO table and appends a concise conflict report when needed. */
  public String format() {
    final List<Integer> widths = new ArrayList<Integer>();
    widths.add("State".length());
    for (final String terminal : actionColumns) {
      widths.add(("ACTION[" + terminal + "]").length());
    }
    for (final String nonterminal : gotoColumns) {
      widths.add(("GOTO[" + nonterminal + "]").length());
    }
    for (int state = 0; state < states.size(); state++) {
      widths.set(0, Math.max(widths.get(0), String.valueOf(state).length()));
      int column = 1;
      for (final String terminal : actionColumns) {
        widths.set(column, Math.max(widths.get(column),
            formatActions(getActions(state, terminal)).length()));
        column++;
      }
      for (final String nonterminal : gotoColumns) {
        final Integer target = getGoto(state, nonterminal);
        widths.set(column, Math.max(widths.get(column),
            target == null ? 0 : String.valueOf(target).length()));
        column++;
      }
    }

    final List<String> headings = new ArrayList<String>();
    for (final String terminal : actionColumns) {
      headings.add("ACTION[" + terminal + "]");
    }
    for (final String nonterminal : gotoColumns) {
      headings.add("GOTO[" + nonterminal + "]");
    }
    final StringBuilder output = new StringBuilder();
    output.append(parserType.getDisplayName()).append(" parsing table")
        .append(System.lineSeparator());
    appendRow(output, widths, "State", headings);
    appendSeparator(output, widths);
    for (int state = 0; state < states.size(); state++) {
      final List<String> values = new ArrayList<String>();
      for (final String terminal : actionColumns) {
        values.add(formatActions(getActions(state, terminal)));
      }
      for (final String nonterminal : gotoColumns) {
        final Integer target = getGoto(state, nonterminal);
        values.add(target == null ? "" : String.valueOf(target));
      }
      appendRow(output, widths, String.valueOf(state), values);
    }
    if (!conflicts.isEmpty()) {
      output.append("Conflicts:").append(System.lineSeparator());
      for (final Conflict conflict : conflicts) {
        output.append("  ").append(conflict).append(System.lineSeparator());
      }
    }
    return output.toString();
  }

  @Override
  public String toString() {
    return format();
  }

  private void checkState(final int state) {
    if (state < 0 || state >= states.size()) {
      throw new IllegalArgumentException("Unknown LR state " + state);
    }
  }

  private static String augmentedStart(final Grammar grammar) {
    String result = grammar.getStartSymbol() + "'";
    while (grammar.getNonterminals().contains(result) || grammar.getTerminals().contains(result)) {
      result += "'";
    }
    return result;
  }

  private static Automaton buildLR0Automaton(final Grammar grammar, final Production augmented) {
    // TODO: implement the body
    return null; // TODO: Replace null with an Automaton instance. You can use the buildAutomaton method to create one.
  }

  private static Automaton buildCanonicalLR1Automaton(final Grammar grammar,
      final GrammarAnalysis analysis, final Production augmented) {
    // TODO: implement the body
    return null; // TODO: Replace null with an Automaton instance. You can use the buildAutomaton method to create one.
  }

  private static Automaton buildAutomaton(final Set<Item> initial, final Grammar grammar,
      final GotoFunction gotoFunction) {
    final List<Set<Item>> states = new ArrayList<Set<Item>>();
    final List<Map<String, Integer>> transitions = new ArrayList<Map<String, Integer>>();
    final Map<Set<Item>, Integer> stateNumbers = new LinkedHashMap<Set<Item>, Integer>();
    addState(states, transitions, stateNumbers, initial);
    final List<String> symbols = transitionSymbols(grammar);
    for (int state = 0; state < states.size(); state++) {
      final Map<String, Integer> row = transitions.get(state);
      for (final String symbol : symbols) {
        final Set<Item> target = gotoFunction.apply(states.get(state), symbol);
        if (!target.isEmpty()) {
          row.put(symbol, addState(states, transitions, stateNumbers, target));
        }
      }
    }
    return new Automaton(states, transitions);
  }

  private static int addState(final List<Set<Item>> states,
      final List<Map<String, Integer>> transitions, final Map<Set<Item>, Integer> stateNumbers,
      final Set<Item> candidate) {
    final Integer existing = stateNumbers.get(candidate);
    if (existing != null) {
      return existing;
    }
    final Set<Item> stored = new LinkedHashSet<Item>(candidate);
    final int number = states.size();
    states.add(stored);
    transitions.add(new LinkedHashMap<String, Integer>());
    stateNumbers.put(stored, number);
    return number;
  }

  private static List<String> transitionSymbols(final Grammar grammar) {
    final List<String> result = new ArrayList<String>(grammar.getTerminals());
    result.addAll(grammar.getNonterminals());
    return result;
  }

  private static Set<Item> closureLR0(final Set<Item> seed, final Grammar grammar) {
    final Set<Item> result = new LinkedHashSet<Item>(seed);
    boolean changed;
    do {
      changed = false;
      for (final Item item : new ArrayList<Item>(result)) {
        final String next = item.symbolAfterDot();
        if (next != null && grammar.getNonterminals().contains(next)) {
          for (final Production production : grammar.getProductions(next)) {
            changed |= result.add(new Item(production, 0, null));
          }
        }
      }
    } while (changed);
    return result;
  }

  private static Set<Item> gotoLR0(final Set<Item> state, final String symbol,
      final Grammar grammar) {
    final Set<Item> moved = new LinkedHashSet<Item>();
    for (final Item item : state) {
      if (symbol.equals(item.symbolAfterDot())) {
        moved.add(item.advance());
      }
    }
    return moved.isEmpty() ? moved : closureLR0(moved, grammar);
  }

  private static Set<Item> closureLr1(final Set<Item> seed, final Grammar grammar,
      final GrammarAnalysis analysis) {
    final Set<Item> result = new LinkedHashSet<Item>(seed);
    boolean changed;
    do {
      changed = false;
      for (final Item item : new ArrayList<Item>(result)) {
        final String next = item.symbolAfterDot();
        if (next == null || !grammar.getNonterminals().contains(next)) {
          continue;
        }
        final List<String> suffix = new ArrayList<String>();
        suffix.addAll(item.production.getRightHandSide().subList(item.dot + 1,
            item.production.getRightHandSide().size()));
        suffix.add(item.lookahead);
        for (final String lookahead : analysis.firstOf(suffix)) {
          if (Grammar.EPSILON.equals(lookahead)) {
            continue;
          }
          for (final Production production : grammar.getProductions(next)) {
            changed |= result.add(new Item(production, 0, lookahead));
          }
        }
      }
    } while (changed);
    return result;
  }

  private static Set<Item> gotoLr1(final Set<Item> state, final String symbol,
      final Grammar grammar, final GrammarAnalysis analysis) {
    final Set<Item> moved = new LinkedHashSet<Item>();
    for (final Item item : state) {
      if (symbol.equals(item.symbolAfterDot())) {
        moved.add(item.advance());
      }
    }
    return moved.isEmpty() ? moved : closureLr1(moved, grammar, analysis);
  }

  private static Automaton mergeLALRStates(final Automaton canonical) {
    // TODO: implement the body
    return null; // TODO: Replace null with an Automaton instance.
  }

  private static Set<Core> coreOf(final Set<Item> state) {
    final Set<Core> result = new LinkedHashSet<Core>();
    for (final Item item : state) {
      result.add(new Core(item.production, item.dot));
    }
    return result;
  }

  private static void populateGotos(final List<Map<String, Integer>> gotos,
      final List<Map<String, Integer>> transitions, final Grammar grammar) {
    for (int state = 0; state < transitions.size(); state++) {
      for (final String nonterminal : grammar.getNonterminals()) {
        final Integer target = transitions.get(state).get(nonterminal);
        if (target != null) {
          gotos.get(state).put(nonterminal, target);
        }
      }
    }
  }

  private static void populateActions(final List<Map<String, List<LrAction>>> actions,
      final List<Map<String, Integer>> transitions, final Grammar grammar,
      final ParserType parserType, final GrammarAnalysis analysis, final Production augmented,
      final List<Set<Item>> states) {
    for (int state = 0; state < states.size(); state++) {
      for (final Item item : states.get(state)) {
        final String next = item.symbolAfterDot();
        if (next != null && grammar.getTerminals().contains(next)) {
          addAction(actions, state, next, LrAction.shift(transitions.get(state).get(next)));
        }
        if (!item.isComplete()) {
          continue;
        }
        if (item.production.getNumber() == augmented.getNumber()) {
          addAction(actions, state, Grammar.END_MARKER, LrAction.accept());
          continue;
        }
        if (parserType == ParserType.SLR1) {
          for (final String terminal : analysis.getFollowSets().get(item.production.getLeftHandSide())) {
            addAction(actions, state, terminal, LrAction.reduce(item.production));
          }
        } else {
          addAction(actions, state, item.lookahead, LrAction.reduce(item.production));
        }
      }
    }
  }

  private static void addAction(final List<Map<String, List<LrAction>>> actions, final int state,
      final String terminal, final LrAction action) {
    List<LrAction> cell = actions.get(state).get(terminal);
    if (cell == null) {
      cell = new ArrayList<LrAction>();
      actions.get(state).put(terminal, cell);
    }
    if (!cell.contains(action)) {
      cell.add(action);
    }
  }

  private static List<Map<String, List<LrAction>>> emptyActionRows(final int count) {
    final List<Map<String, List<LrAction>>> result = new ArrayList<Map<String, List<LrAction>>>();
    for (int i = 0; i < count; i++) {
      result.add(new LinkedHashMap<String, List<LrAction>>());
    }
    return result;
  }

  private static List<Map<String, Integer>> emptyGotoRows(final int count) {
    final List<Map<String, Integer>> result = new ArrayList<Map<String, Integer>>();
    for (int i = 0; i < count; i++) {
      result.add(new LinkedHashMap<String, Integer>());
    }
    return result;
  }

  private static List<Set<Item>> immutableStates(final List<Set<Item>> source) {
    final List<Set<Item>> result = new ArrayList<Set<Item>>();
    for (final Set<Item> state : source) {
      result.add(Collections.unmodifiableSet(new LinkedHashSet<Item>(state)));
    }
    return Collections.unmodifiableList(result);
  }

  private static List<Map<String, List<LrAction>>> immutableActionRows(
      final List<Map<String, List<LrAction>>> source) {
    final List<Map<String, List<LrAction>>> result =
        new ArrayList<Map<String, List<LrAction>>>();
    for (final Map<String, List<LrAction>> row : source) {
      final Map<String, List<LrAction>> copiedRow = new LinkedHashMap<String, List<LrAction>>();
      for (final Map.Entry<String, List<LrAction>> cell : row.entrySet()) {
        copiedRow.put(cell.getKey(), Collections.unmodifiableList(
            new ArrayList<LrAction>(cell.getValue())));
      }
      result.add(Collections.unmodifiableMap(copiedRow));
    }
    return Collections.unmodifiableList(result);
  }

  private static List<Map<String, Integer>> immutableGotoRows(
      final List<Map<String, Integer>> source) {
    final List<Map<String, Integer>> result = new ArrayList<Map<String, Integer>>();
    for (final Map<String, Integer> row : source) {
      result.add(Collections.unmodifiableMap(new LinkedHashMap<String, Integer>(row)));
    }
    return Collections.unmodifiableList(result);
  }

  private static List<Conflict> findConflicts(
      final List<Map<String, List<LrAction>>> actions) {
    final List<Conflict> result = new ArrayList<Conflict>();
    for (int state = 0; state < actions.size(); state++) {
      for (final Map.Entry<String, List<LrAction>> cell : actions.get(state).entrySet()) {
        if (cell.getValue().size() > 1) {
          result.add(new Conflict(state, cell.getKey(), cell.getValue()));
        }
      }
    }
    return Collections.unmodifiableList(result);
  }

  private static String formatActions(final List<LrAction> actions) {
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < actions.size(); i++) {
      if (i > 0) {
        result.append(" / ");
      }
      result.append(actions.get(i));
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

  private interface GotoFunction {
    Set<Item> apply(Set<Item> state, String symbol);
  }

  private static final class Automaton {
    private final List<Set<Item>> states;
    private final List<Map<String, Integer>> transitions;

    Automaton(final List<Set<Item>> states, final List<Map<String, Integer>> transitions) {
      this.states = states;
      this.transitions = transitions;
    }
  }

  private static final class Core {
    private final Production production;
    private final int dot;

    Core(final Production production, final int dot) {
      this.production = production;
      this.dot = dot;
    }

    @Override
    public boolean equals(final Object other) {
      if (!(other instanceof Core)) {
        return false;
      }
      final Core that = (Core) other;
      return production.getNumber() == that.production.getNumber() && dot == that.dot;
    }

    @Override
    public int hashCode() {
      return 31 * production.getNumber() + dot;
    }
  }

  private static final class Item {
    private final Production production;
    private final int dot;
    private final String lookahead;

    Item(final Production production, final int dot, final String lookahead) {
      this.production = production;
      this.dot = dot;
      this.lookahead = lookahead;
    }

    String symbolAfterDot() {
      return dot == production.getRightHandSide().size() ? null
          : production.getRightHandSide().get(dot);
    }

    boolean isComplete() {
      return dot == production.getRightHandSide().size();
    }

    Item advance() {
      return new Item(production, dot + 1, lookahead);
    }

    @Override
    public boolean equals(final Object other) {
      if (!(other instanceof Item)) {
        return false;
      }
      final Item that = (Item) other;
      if (production.getNumber() != that.production.getNumber() || dot != that.dot) {
        return false;
      }
      return lookahead == null ? that.lookahead == null : lookahead.equals(that.lookahead);
    }

    @Override
    public int hashCode() {
      return 31 * (31 * production.getNumber() + dot) + (lookahead == null ? 0 : lookahead.hashCode());
    }

    @Override
    public String toString() {
      final StringBuilder result = new StringBuilder();
      result.append(production.getLeftHandSide()).append(" ->");
      final List<String> right = production.getRightHandSide();
      for (int i = 0; i <= right.size(); i++) {
        if (i == dot) {
          result.append(" ·");
        }
        if (i < right.size()) {
          result.append(' ').append(right.get(i));
        }
      }
      if (lookahead != null) {
        result.append(", ").append(lookahead);
      }
      return result.toString();
    }
  }

  /** A table cell with two or more applicable actions. */
  public static final class Conflict {
    private final int state;
    private final String terminal;
    private final List<LrAction> actions;

    private Conflict(final int state, final String terminal, final List<LrAction> actions) {
      this.state = state;
      this.terminal = terminal;
      this.actions = Collections.unmodifiableList(new ArrayList<LrAction>(actions));
    }

    public int getState() {
      return state;
    }

    public String getTerminal() {
      return terminal;
    }

    public List<LrAction> getActions() {
      return actions;
    }

    /** A human-readable classification such as {@code shift/reduce}. */
    public String getKind() {
      boolean shift = false;
      int reductions = 0;
      for (final LrAction action : actions) {
        if (action.getKind() == LrAction.Kind.SHIFT) {
          shift = true;
        } else if (action.getKind() == LrAction.Kind.REDUCE) {
          reductions++;
        }
      }
      if (shift && reductions > 0) {
        return "shift/reduce";
      }
      if (reductions > 1) {
        return "reduce/reduce";
      }
      return "multiple-action";
    }

    @Override
    public String toString() {
      return "ACTION[" + state + ", " + terminal + "] = " + formatActions(actions)
          + " (" + getKind() + " conflict)";
    }
  }
}
