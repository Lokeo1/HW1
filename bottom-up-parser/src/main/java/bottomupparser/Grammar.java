package bottomupparser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An immutable context-free grammar using the same JSON schema as the top-down-parser module.
 *
 * <p>The shared reader also means grammar validation (including the reserved {@code $} and
 * epsilon symbols) is identical in both modules. This class exposes bottom-up-parser value
 * objects, so clients do not need a dependency on the top-down implementation.
 */
public final class Grammar {
  /** Symbol used to display an empty production. */
  public static final String EPSILON = topdownparser.Grammar.EPSILON;
  /** End-of-input terminal used by LR ACTION tables. */
  public static final String END_MARKER = topdownparser.Grammar.END_MARKER;

  private final String startSymbol;
  private final Set<String> nonterminals;
  private final Set<String> terminals;
  private final List<Production> productions;
  private final Map<String, List<Production>> productionsByLeftHandSide;

  private Grammar(final topdownparser.Grammar source) {
    startSymbol = source.getStartSymbol();
    nonterminals = Collections.unmodifiableSet(new LinkedHashSet<String>(source.getNonterminals()));
    terminals = Collections.unmodifiableSet(new LinkedHashSet<String>(source.getTerminals()));

    final List<Production> converted = new ArrayList<Production>();
    final Map<String, List<Production>> grouped = new LinkedHashMap<String, List<Production>>();
    for (final String nonterminal : nonterminals) {
      grouped.put(nonterminal, new ArrayList<Production>());
    }
    for (final topdownparser.Production production : source.getProductions()) {
      final Production copy = new Production(production.getNumber(), production.getLeftHandSide(),
          production.getRightHandSide());
      converted.add(copy);
      grouped.get(copy.getLeftHandSide()).add(copy);
    }
    productions = Collections.unmodifiableList(converted);

    final Map<String, List<Production>> immutableGrouped =
        new LinkedHashMap<String, List<Production>>();
    for (final Map.Entry<String, List<Production>> entry : grouped.entrySet()) {
      immutableGrouped.put(entry.getKey(), Collections.unmodifiableList(
          new ArrayList<Production>(entry.getValue())));
    }
    productionsByLeftHandSide = Collections.unmodifiableMap(immutableGrouped);
  }

  /** Reads a UTF-8 grammar file in the top-down-parser JSON format. */
  public static Grammar fromJson(final Path file) throws IOException {
    return new Grammar(topdownparser.Grammar.fromJson(file));
  }

  /** Parses a grammar encoded in the top-down-parser JSON format. */
  public static Grammar fromJson(final String json) {
    return new Grammar(topdownparser.Grammar.fromJson(json));
  }

  public String getStartSymbol() {
    return startSymbol;
  }

  public Set<String> getNonterminals() {
    return nonterminals;
  }

  public Set<String> getTerminals() {
    return terminals;
  }

  public List<Production> getProductions() {
    return productions;
  }

  /** Returns the productions whose left hand side is {@code nonterminal}. */
  public List<Production> getProductions(final String nonterminal) {
    final List<Production> result = productionsByLeftHandSide.get(nonterminal);
    if (result == null) {
      throw new IllegalArgumentException("Unknown nonterminal '" + nonterminal + "'");
    }
    return result;
  }
}
