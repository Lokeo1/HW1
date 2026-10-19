package bottomupparser;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** FIRST and FOLLOW sets used by the LR table builders. */
final class GrammarAnalysis {
  private final Grammar grammar;
  private final Map<String, Set<String>> firstSets;
  private final Map<String, Set<String>> followSets;

  private GrammarAnalysis(final Grammar grammar, final Map<String, Set<String>> firstSets,
      final Map<String, Set<String>> followSets) {
    this.grammar = grammar;
    this.firstSets = immutable(firstSets);
    this.followSets = immutable(followSets);
  }

  static GrammarAnalysis analyze(final Grammar grammar) {
    final Map<String, Set<String>> first = emptySets(grammar);
    boolean changed;
    do {
      changed = false;
      for (final Production production : grammar.getProductions()) {
        changed |= first.get(production.getLeftHandSide()).addAll(
            firstOf(production.getRightHandSide(), grammar, first));
      }
    } while (changed);

    final Map<String, Set<String>> follow = emptySets(grammar);
    follow.get(grammar.getStartSymbol()).add(Grammar.END_MARKER);
    do {
      changed = false;
      for (final Production production : grammar.getProductions()) {
        final List<String> right = production.getRightHandSide();
        for (int i = 0; i < right.size(); i++) {
          final String symbol = right.get(i);
          if (!grammar.getNonterminals().contains(symbol)) {
            continue;
          }
          final Set<String> suffixFirst = firstOf(right.subList(i + 1, right.size()), grammar,
              first);
          changed |= addWithoutEpsilon(follow.get(symbol), suffixFirst);
          if (suffixFirst.contains(Grammar.EPSILON)) {
            changed |= follow.get(symbol).addAll(follow.get(production.getLeftHandSide()));
          }
        }
      }
    } while (changed);
    return new GrammarAnalysis(grammar, first, follow);
  }

  Map<String, Set<String>> getFirstSets() {
    return firstSets;
  }

  Map<String, Set<String>> getFollowSets() {
    return followSets;
  }

  /** FIRST of a sequence that may also end with {@code $}. */
  Set<String> firstOf(final List<String> symbols) {
    return firstOf(symbols, grammar, firstSets);
  }

  private static Map<String, Set<String>> emptySets(final Grammar grammar) {
    final Map<String, Set<String>> result = new LinkedHashMap<String, Set<String>>();
    for (final String nonterminal : grammar.getNonterminals()) {
      result.put(nonterminal, new LinkedHashSet<String>());
    }
    return result;
  }

  private static Set<String> firstOf(final List<String> symbols, final Grammar grammar,
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

  private static Map<String, Set<String>> immutable(final Map<String, Set<String>> source) {
    final Map<String, Set<String>> result = new LinkedHashMap<String, Set<String>>();
    for (final Map.Entry<String, Set<String>> entry : source.entrySet()) {
      result.put(entry.getKey(), Collections.unmodifiableSet(
          new LinkedHashSet<String>(entry.getValue())));
    }
    return Collections.unmodifiableMap(result);
  }
}
