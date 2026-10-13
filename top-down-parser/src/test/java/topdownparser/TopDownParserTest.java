package topdownparser;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/** JUnit tests for table construction and table-driven top-down parsing. */
public class TopDownParserTest {

  @Test
  public void testValidInputForValidGrammar() throws Exception {
    final TopDownParser parser = parser("valid-grammar.json");

    assertParses(parser, "123");
    assertParses(parser, "123 + 456");
    assertParses(parser, "(123 + 456)");
    assertParses(parser, "123 * 456");
    assertParses(parser, "123 * 456 + 7 * (8 + 9)");
  }

  @Test
  public void testInvalidInputForValidGrammar() throws Exception {
    final TopDownParser parser = parser("valid-grammar.json");

    assertDoesNotParse(parser, "+123");
    assertDoesNotParse(parser, "123 +");
    assertDoesNotParse(parser, "123 ** 456");
    assertDoesNotParse(parser, "(123 + 456");
  }

  @Test
  public void checksFirstSetsForValidGrammar() throws Exception {
    final Ll1ParsingTable table = parser("valid-grammar.json").getTable();

    assertFirstSet(table, "E", "INT", "(");
    assertFirstSet(table, "E'", "+", Grammar.EPSILON);
    assertFirstSet(table, "T", "INT", "(");
    assertFirstSet(table, "T'", "*", Grammar.EPSILON);
    assertFirstSet(table, "F", "INT", "(");
  }

  @Test
  public void checksFollowSetsForValidGrammar() throws Exception {
    final Ll1ParsingTable table = parser("valid-grammar.json").getTable();

    assertFollowSet(table, "E", ")", Grammar.END_MARKER);
    assertFollowSet(table, "E'", ")", Grammar.END_MARKER);
    assertFollowSet(table, "T", "+", ")", Grammar.END_MARKER);
    assertFollowSet(table, "T'", "+", ")", Grammar.END_MARKER);
    assertFollowSet(table, "F", "*", "+", ")", Grammar.END_MARKER);
  }

  @Test
  public void testInputForInvalidGrammar() throws Exception {
    final TopDownParser parser = parser("invalid-grammar.json");

    assertParses(parser, "123");
    assertDoesNotParse(parser, "123 + 456");
    assertDoesNotParse(parser, "(123 + 456)");
    assertDoesNotParse(parser, "123 * 456");
    assertDoesNotParse(parser, "123 * 456 + 7 * (8 + 9)");
  }

  @Test
  public void checksConflictingProductionsForInvalidGrammar() throws Exception {
    final Ll1ParsingTable table = parser("invalid-grammar.json").getTable();

    Assert.assertFalse(table.isLl1());
    Assert.assertEquals(2, table.getConflicts().size());
    assertConflictCell(table, "X", "+", "X -> E'", "X -> + INT E'");
    assertConflictCell(table, "T'", "*", "T' -> * T T'", "T' -> ε");
  }

  @Test
  public void checksFirstSetsForInvalidGrammar() throws Exception {
    final Ll1ParsingTable table = parser("invalid-grammar.json").getTable();

    assertFirstSet(table, "E", "INT", "(");
    assertFirstSet(table, "E'", "+", Grammar.EPSILON);
    assertFirstSet(table, "T", "INT", "(");
    assertFirstSet(table, "T'", "*", Grammar.EPSILON);
    assertFirstSet(table, "X", "+", Grammar.EPSILON);
  }

  @Test
  public void checksFollowSetsForInvalidGrammar() throws Exception {
    final Ll1ParsingTable table = parser("invalid-grammar.json").getTable();

    assertFollowSet(table, "E", ")", Grammar.END_MARKER);
    assertFollowSet(table, "E'", ")", Grammar.END_MARKER);
    assertFollowSet(table, "T", "+", "*", ")", Grammar.END_MARKER);
    assertFollowSet(table, "T'", "+", ")", "*", Grammar.END_MARKER);
    assertFollowSet(table, "X", ")", Grammar.END_MARKER);
  }

  private static TopDownParser parser(final String name) throws Exception {
    return TopDownParser.fromJson(resource(name));
  }

  private static Path resource(final String name) throws URISyntaxException {
    return Paths.get(TopDownParserTest.class.getResource("/topdownparser/" + name).toURI());
  }

  private static void assertParses(final TopDownParser parser, final String input) {
    final ParseResult result = parser.parse(input);
    Assert.assertTrue("Expected input '" + input + "' to parse, but got: " + result,
        result.isSuccess());
  }

  private static void assertDoesNotParse(final TopDownParser parser, final String input) {
    final ParseResult result = parser.parse(input);
    Assert.assertFalse("Expected input '" + input + "' to fail, but got: " + result,
        result.isSuccess());
  }

  private static void assertFirstSet(final Ll1ParsingTable table, final String nonterminal,
      final String... expected) {
    Assert.assertEquals("Unexpected FIRST(" + nonterminal + ")",
        new HashSet<String>(Arrays.asList(expected)), table.getFirstSets().get(nonterminal));
  }

  private static void assertFollowSet(final Ll1ParsingTable table, final String nonterminal,
      final String... expected) {
    Assert.assertEquals("Unexpected FOLLOW(" + nonterminal + ")",
        new HashSet<String>(Arrays.asList(expected)), table.getFollowSets().get(nonterminal));
  }

  private static void assertConflictCell(final Ll1ParsingTable table, final String nonterminal,
      final String lookahead, final String... expectedProductions) {
    final List<String> actual = new ArrayList<String>();
    for (final Production production : table.getCell(nonterminal, lookahead)) {
      actual.add(production.toString());
    }
    Assert.assertEquals("Unexpected productions in M[" + nonterminal + ", " + lookahead + "]",
        Arrays.asList(expectedProductions), actual);
  }
}
