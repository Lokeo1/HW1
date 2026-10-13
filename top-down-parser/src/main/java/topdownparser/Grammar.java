package topdownparser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An immutable context-free grammar read from a JSON file.
 *
 * <p>The required JSON properties are {@code start} and {@code productions}. A production
 * object maps each nonterminal to an array of alternatives, and each alternative is an array of
 * symbols. An empty alternative denotes epsilon. For example:
 *
 * <pre>
 * {
 *   "start": "S",
 *   "terminals": ["IF", "ID", "INT"],
 *   "productions": {
 *     "S": [["IF", "ID"], ["INT"]]
 *   }
 * }
 * </pre>
 *
 * <p>{@code nonterminals} is optional and is inferred from the production keys. Likewise,
 * {@code terminals} is optional and is inferred from right-hand-side symbols that are not
 * nonterminals. The parser reserves {@value #END_MARKER} for end of input and uses
 * {@value #EPSILON} only when displaying epsilon; neither can occur in a right-hand side.
 */
public final class Grammar {
  /** Symbol used to display an empty production. */
  public static final String EPSILON = "ε";
  /** Terminal used by the parsing table for end of input. */
  public static final String END_MARKER = "$";

  private final String startSymbol;
  private final Set<String> nonterminals;
  private final Set<String> terminals;
  private final List<Production> productions;
  private final Map<String, List<Production>> productionsByLeftHandSide;

  private Grammar(final String startSymbol, final Set<String> nonterminals,
      final Set<String> terminals, final List<Production> productions) {
    this.startSymbol = startSymbol;
    this.nonterminals = Collections.unmodifiableSet(new LinkedHashSet<String>(nonterminals));
    this.terminals = Collections.unmodifiableSet(new LinkedHashSet<String>(terminals));
    this.productions = Collections.unmodifiableList(new ArrayList<Production>(productions));

    final Map<String, List<Production>> grouped = new LinkedHashMap<String, List<Production>>();
    for (final String nonterminal : nonterminals) {
      grouped.put(nonterminal, new ArrayList<Production>());
    }
    for (final Production production : productions) {
      grouped.get(production.getLeftHandSide()).add(production);
    }
    final Map<String, List<Production>> immutableGrouped =
        new LinkedHashMap<String, List<Production>>();
    for (final Map.Entry<String, List<Production>> entry : grouped.entrySet()) {
      immutableGrouped.put(entry.getKey(),
          Collections.unmodifiableList(new ArrayList<Production>(entry.getValue())));
    }
    this.productionsByLeftHandSide = Collections.unmodifiableMap(immutableGrouped);
  }

  /** Reads a UTF-8 grammar file. */
  public static Grammar fromJson(final Path file) throws IOException {
    if (file == null) {
      throw new IllegalArgumentException("file must not be null");
    }
    return fromJson(new String(Files.readAllBytes(file), StandardCharsets.UTF_8));
  }

  /** Parses a grammar encoded with the documented JSON schema. */
  public static Grammar fromJson(final String json) {
    if (json == null) {
      throw new IllegalArgumentException("json must not be null");
    }
    final Object parsed = new JsonReader(json).read();
    final Map<String, Object> root = asObject(parsed, "the JSON root");

    final String startSymbol = requiredString(root, "start");
    final Map<String, Object> rawProductions =
        asObject(required(root, "productions"), "'productions'");
    final Set<String> nonterminals = root.containsKey("nonterminals")
        ? stringSet(root.get("nonterminals"), "'nonterminals'")
        : new LinkedHashSet<String>(rawProductions.keySet());
    if (nonterminals.isEmpty()) {
      throw new GrammarFormatException("'nonterminals' must not be empty");
    }
    if (!nonterminals.contains(startSymbol)) {
      throw new GrammarFormatException("Start symbol '" + startSymbol
          + "' is not a nonterminal");
    }
    for (final String leftHandSide : rawProductions.keySet()) {
      if (!nonterminals.contains(leftHandSide)) {
        throw new GrammarFormatException("Production for undeclared nonterminal '"
            + leftHandSide + "'");
      }
    }
    rejectReserved(nonterminals, "nonterminal");

    final List<RawProduction> rawAlternatives = new ArrayList<RawProduction>();
    for (final Map.Entry<String, Object> entry : rawProductions.entrySet()) {
      final List<Object> alternatives = asArray(entry.getValue(),
          "the alternatives for '" + entry.getKey() + "'");
      for (int i = 0; i < alternatives.size(); i++) {
        final List<Object> rawRightHandSide = asArray(alternatives.get(i),
            "alternative " + (i + 1) + " for '" + entry.getKey() + "'");
        final List<String> rightHandSide = new ArrayList<String>();
        for (final Object symbol : rawRightHandSide) {
          rightHandSide.add(asString(symbol, "a production symbol"));
        }
        normalizeEpsilon(rightHandSide, entry.getKey());
        rawAlternatives.add(new RawProduction(entry.getKey(), rightHandSide));
      }
    }

    final Set<String> terminals;
    if (root.containsKey("terminals")) {
      terminals = stringSet(root.get("terminals"), "'terminals'");
      rejectReserved(terminals, "terminal");
    } else {
      terminals = new LinkedHashSet<String>();
      for (final RawProduction production : rawAlternatives) {
        for (final String symbol : production.rightHandSide) {
          if (!nonterminals.contains(symbol)) {
            terminals.add(symbol);
          }
        }
      }
    }
    for (final String terminal : terminals) {
      if (nonterminals.contains(terminal)) {
        throw new GrammarFormatException("Symbol '" + terminal
            + "' cannot be both a terminal and a nonterminal");
      }
    }
    for (final RawProduction production : rawAlternatives) {
      for (final String symbol : production.rightHandSide) {
        if (!nonterminals.contains(symbol) && !terminals.contains(symbol)) {
          throw new GrammarFormatException("Unknown symbol '" + symbol + "' in "
              + production.leftHandSide + " -> " + display(production.rightHandSide));
        }
      }
    }

    final List<Production> productions = new ArrayList<Production>();
    for (int i = 0; i < rawAlternatives.size(); i++) {
      final RawProduction rawProduction = rawAlternatives.get(i);
      productions.add(new Production(i, rawProduction.leftHandSide, rawProduction.rightHandSide));
    }
    return new Grammar(startSymbol, nonterminals, terminals, productions);
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

  /** Returns all productions with the supplied nonterminal on their left-hand side. */
  public List<Production> getProductions(final String nonterminal) {
    final List<Production> result = productionsByLeftHandSide.get(nonterminal);
    if (result == null) {
      throw new IllegalArgumentException("Unknown nonterminal '" + nonterminal + "'");
    }
    return result;
  }

  private static void normalizeEpsilon(final List<String> symbols, final String leftHandSide) {
    if (symbols.size() == 1 && (EPSILON.equals(symbols.get(0)) || "epsilon".equals(symbols.get(0)))) {
      symbols.clear();
      return;
    }
    for (final String symbol : symbols) {
      if (EPSILON.equals(symbol) || "epsilon".equals(symbol) || END_MARKER.equals(symbol)) {
        throw new GrammarFormatException("Reserved symbol '" + symbol + "' cannot appear in "
            + leftHandSide + " -> " + display(symbols));
      }
    }
  }

  private static String display(final List<String> symbols) {
    if (symbols.isEmpty()) {
      return EPSILON;
    }
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < symbols.size(); i++) {
      if (i > 0) {
        result.append(' ');
      }
      result.append(symbols.get(i));
    }
    return result.toString();
  }

  private static void rejectReserved(final Set<String> symbols, final String kind) {
    for (final String symbol : symbols) {
      if (EPSILON.equals(symbol) || "epsilon".equals(symbol) || END_MARKER.equals(symbol)) {
        throw new GrammarFormatException("Reserved symbol '" + symbol + "' cannot be a " + kind);
      }
    }
  }

  private static Object required(final Map<String, Object> object, final String name) {
    if (!object.containsKey(name)) {
      throw new GrammarFormatException("Missing required property " + name);
    }
    return object.get(name);
  }

  private static String requiredString(final Map<String, Object> object, final String name) {
    return asString(required(object, name), "'" + name + "'");
  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> asObject(final Object value, final String description) {
    if (!(value instanceof Map)) {
      throw new GrammarFormatException("Expected an object for " + description);
    }
    return (Map<String, Object>) value;
  }

  @SuppressWarnings("unchecked")
  private static List<Object> asArray(final Object value, final String description) {
    if (!(value instanceof List)) {
      throw new GrammarFormatException("Expected an array for " + description);
    }
    return (List<Object>) value;
  }

  private static Set<String> stringSet(final Object value, final String description) {
    final Set<String> result = new LinkedHashSet<String>();
    for (final Object element : asArray(value, description)) {
      final String string = asString(element, "an element of " + description);
      if (!result.add(string)) {
        throw new GrammarFormatException("Duplicate symbol '" + string + "' in " + description);
      }
    }
    return result;
  }

  private static String asString(final Object value, final String description) {
    if (!(value instanceof String) || ((String) value).isEmpty()) {
      throw new GrammarFormatException("Expected a non-empty string for " + description);
    }
    return (String) value;
  }

  private static final class RawProduction {
    private final String leftHandSide;
    private final List<String> rightHandSide;

    RawProduction(final String leftHandSide, final List<String> rightHandSide) {
      this.leftHandSide = leftHandSide;
      this.rightHandSide = rightHandSide;
    }
  }

  /** Thrown when a JSON document is not a grammar in this module's format. */
  public static final class GrammarFormatException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    GrammarFormatException(final String message) {
      super(message);
    }
  }

  /** Small, dependency-free JSON reader for grammar files. */
  private static final class JsonReader {
    private final String source;
    private int offset;

    JsonReader(final String source) {
      this.source = source;
    }

    Object read() {
      final Object result = value();
      whitespace();
      if (offset != source.length()) {
        error("Unexpected trailing input");
      }
      return result;
    }

    private Object value() {
      whitespace();
      if (offset == source.length()) {
        error("Expected a JSON value");
      }
      final char character = source.charAt(offset);
      if (character == '{') {
        return object();
      }
      if (character == '[') {
        return array();
      }
      if (character == '"') {
        return string();
      }
      error("Expected an object, array, or string");
      return null; // Unreachable, but required by javac.
    }

    private Map<String, Object> object() {
      expect('{');
      final Map<String, Object> result = new LinkedHashMap<String, Object>();
      whitespace();
      if (consume('}')) {
        return result;
      }
      do {
        whitespace();
        if (offset == source.length() || source.charAt(offset) != '"') {
          error("Expected an object property name");
        }
        final String name = string();
        if (result.containsKey(name)) {
          error("Duplicate object property '" + name + "'");
        }
        whitespace();
        expect(':');
        result.put(name, value());
        whitespace();
      } while (consume(','));
      expect('}');
      return result;
    }

    private List<Object> array() {
      expect('[');
      final List<Object> result = new ArrayList<Object>();
      whitespace();
      if (consume(']')) {
        return result;
      }
      do {
        result.add(value());
        whitespace();
      } while (consume(','));
      expect(']');
      return result;
    }

    private String string() {
      expect('"');
      final StringBuilder result = new StringBuilder();
      while (offset < source.length()) {
        final char character = source.charAt(offset++);
        if (character == '"') {
          return result.toString();
        }
        if (character == '\\') {
          if (offset == source.length()) {
            error("Unterminated escape sequence");
          }
          final char escaped = source.charAt(offset++);
          switch (escaped) {
            case '"': result.append('"'); break;
            case '\\': result.append('\\'); break;
            case '/': result.append('/'); break;
            case 'b': result.append('\b'); break;
            case 'f': result.append('\f'); break;
            case 'n': result.append('\n'); break;
            case 'r': result.append('\r'); break;
            case 't': result.append('\t'); break;
            case 'u': result.append(unicode()); break;
            default: error("Invalid escape sequence '\\" + escaped + "'");
          }
        } else {
          if (character < 0x20) {
            error("Unescaped control character in string");
          }
          result.append(character);
        }
      }
      error("Unterminated string");
      return null; // Unreachable, but required by javac.
    }

    private char unicode() {
      if (offset + 4 > source.length()) {
        error("Incomplete unicode escape");
      }
      final String digits = source.substring(offset, offset + 4);
      try {
        final char result = (char) Integer.parseInt(digits, 16);
        offset += 4;
        return result;
      } catch (final NumberFormatException exception) {
        error("Invalid unicode escape");
        return 0; // Unreachable, but required by javac.
      }
    }

    private void expect(final char expected) {
      whitespace();
      if (offset == source.length() || source.charAt(offset) != expected) {
        error("Expected '" + expected + "'");
      }
      offset++;
    }

    private boolean consume(final char expected) {
      if (offset < source.length() && source.charAt(offset) == expected) {
        offset++;
        return true;
      }
      return false;
    }

    private void whitespace() {
      while (offset < source.length() && Character.isWhitespace(source.charAt(offset))) {
        offset++;
      }
    }

    private void error(final String message) {
      throw new GrammarFormatException(message + " at JSON offset " + offset);
    }
  }
}
