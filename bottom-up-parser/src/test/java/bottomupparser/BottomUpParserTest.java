package bottomupparser;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

/** JUnit tests for SLR(1), canonical LR(1), and LALR(1) table-driven parsing. */
public class BottomUpParserTest {

  @Test
  public void SLRTest() throws Exception {
    final BottomUpParser parser = parser("valid-grammar.json", ParserType.SLR1);

    Assert.assertTrue(parser.getTable().isConflictFree());
    assertParses(parser, "123");
    assertParses(parser, "123 * 456 + 7 * (8 + 9)");
    assertDoesNotParse(parser, "+ 123");
    assertDoesNotParse(parser, "123 +");
    assertDoesNotParse(parser, "(123 + 456");
  }

  @Test
  public void LRTest() throws Exception {
    final BottomUpParser parser = parser("valid-grammar.json", ParserType.LR1);

    Assert.assertTrue(parser.getTable().isConflictFree());
    assertParses(parser, "123 * 456 + 7 * (8 + 9)");
    assertDoesNotParse(parser, "123 ** 456");
  }

  @Test
  public void LALRTest() throws Exception {
    final BottomUpParser parser = parser("valid-grammar.json", ParserType.LALR1);

    Assert.assertTrue(parser.getTable().isConflictFree());
    assertParses(parser, "123 * 456 + 7 * (8 + 9)");
    assertDoesNotParse(parser, "123 + )");
  }

  @Test
  public void NotSLRTest() throws Exception {
    final BottomUpParser slr1 = parser("not-slr-grammar.json", ParserType.SLR1);
    final BottomUpParser lr1 = parser("not-slr-grammar.json", ParserType.LR1);
    final BottomUpParser lalr1 = parser("not-slr-grammar.json", ParserType.LALR1);

    Assert.assertFalse(slr1.getTable().isConflictFree());
    Assert.assertEquals(1, slr1.getTable().getConflicts().size());
    Assert.assertTrue(slr1.getTable().format().contains("shift/reduce conflict"));

    Assert.assertTrue(lr1.getTable().isConflictFree());
    Assert.assertTrue(lr1.getTable().getConflicts().isEmpty());
    Assert.assertTrue(lalr1.getTable().isConflictFree());
    Assert.assertTrue(lalr1.getTable().getConflicts().isEmpty());
    assertParses(lr1, "id");
    assertParses(lr1, "* id");
    assertParses(lr1, "* * id");
    assertParses(lr1, "* * * id");
    assertParses(lr1, "id = id");
    assertParses(lr1, "* id = * * id");
    assertDoesNotParse(lr1, "*");
    assertDoesNotParse(lr1, "id id");
    assertDoesNotParse(lr1, "id =");
    assertParses(lalr1, "id");
    assertParses(lalr1, "* id");
    assertParses(lalr1, "* * id");
    assertParses(lalr1, "* * * id");
    assertParses(lalr1, "id = id");
    assertParses(lalr1, "* id = * * id");
    assertDoesNotParse(lalr1, "*");
    assertDoesNotParse(lalr1, "id id");
    assertDoesNotParse(lalr1, "id =");
    final ParseResult slrResult = slr1.parse("id = id");
    Assert.assertFalse(slrResult.isSuccess());
    Assert.assertTrue(slrResult.getMessage().contains("conflict"));
  }

  @Test
  public void OnlyLRTest() throws Exception {
    final BottomUpParser slr1 = parser("only-lr-grammar.json", ParserType.SLR1);
    final BottomUpParser lr1 = parser("only-lr-grammar.json", ParserType.LR1);
    final BottomUpParser lalr1 = parser("only-lr-grammar.json", ParserType.LALR1);

    Assert.assertFalse(slr1.getTable().isConflictFree());
    Assert.assertTrue(slr1.getTable().format().contains("reduce/reduce conflict"));
    assertDoesNotParse(slr1, "if * 1");
    Assert.assertTrue(lr1.getTable().isConflictFree());
    assertParses(lr1, "if * 1");
    assertParses(lr1, "x * +");
    Assert.assertFalse(lalr1.getTable().isConflictFree());
    Assert.assertTrue(lalr1.getTable().format().contains("reduce/reduce conflict"));
    assertDoesNotParse(lalr1, "if * 1");
  }

  private static BottomUpParser parser(final String name, final ParserType type) throws Exception {
    return BottomUpParser.fromJson(resource(name), type);
  }

  private static Path resource(final String name) throws URISyntaxException {
    return Paths.get(BottomUpParserTest.class.getResource("/bottomupparser/" + name).toURI());
  }

  private static void assertParses(final BottomUpParser parser, final String input) {
    final ParseResult result = parser.parse(input);
    Assert.assertTrue("Expected input '" + input + "' to parse, but got: " + result,
        result.isSuccess());
  }

  private static void assertDoesNotParse(final BottomUpParser parser, final String input) {
    final ParseResult result = parser.parse(input);
    Assert.assertFalse("Expected input '" + input + "' to fail, but got: " + result,
        result.isSuccess());
  }

  private static void assertActionKinds(final LrParsingTable.Conflict conflict,
      final LrAction.Kind... expected) {
    Assert.assertEquals(expected.length, conflict.getActions().size());
    for (final LrAction.Kind kind : expected) {
      boolean found = false;
      for (final LrAction action : conflict.getActions()) {
        found |= action.getKind() == kind;
      }
      Assert.assertTrue("Expected " + kind + " action in " + conflict, found);
    }
  }
}
