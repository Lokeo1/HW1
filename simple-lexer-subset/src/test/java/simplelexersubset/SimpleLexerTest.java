package simplelexersubset;

import org.junit.Assert;
import org.junit.Test;

/** JUnit tests for {@link SimpleLexer}. */
public class SimpleLexerTest {
  @Test
  public void scansZero() {
    assertToken("0", Token.Type.INT, "0");
  }

  @Test
  public void scansNonzeroInteger() {
    assertToken("10", Token.Type.INT, "10");
  }

  @Test
  public void scansLeadingZeroAsSeparateIntegers() {
    assertTokenSequence("05", Token.Type.INT, "0", Token.Type.INT, "5");
  }

  @Test
  public void scansIdentifierStartingWithLetter() {
    assertToken("ABC", Token.Type.ID, "ABC");
  }

  @Test
  public void scansIdentifierStartingWithUnderscore() {
    assertToken("_count", Token.Type.ID, "_count");
  }

  @Test
  public void scansIdentifierWithDigits() {
    assertToken("a_1", Token.Type.ID, "a_1");
  }

  @Test
  public void scansIfKeyword() {
    assertToken("if", Token.Type.IF, "if");
  }

  @Test
  public void scansIfPrefixAsIdentifierWhenFollowedByIdentifierCharacter() {
    assertToken("ifx", Token.Type.ID, "ifx");
  }

  @Test
  public void skipsWhitespace() {
    assertToken(" \t\nname", Token.Type.ID, "name");
  }

  @Test(expected = SimpleLexer.LexicalException.class)
  public void rejectsInvalidCharacters() {
    new SimpleLexer("$").nextToken();
  }

  private static void assertToken(final String input, final Token.Type type,
      final String text) {
    final SimpleLexer lexer = new SimpleLexer(input);
    final Token token = lexer.nextToken();
    Assert.assertEquals("Unexpected token type for input '" + input + "'", type,
        token.getType());
    Assert.assertEquals("Unexpected token text for input '" + input + "'", text,
        token.getText());
    Assert.assertEquals(Token.Type.EOF, lexer.nextToken().getType());
  }

  private static void assertTokenSequence(final String input, final Object... expected) {
    final SimpleLexer lexer = new SimpleLexer(input);
    for (int i = 0; i < expected.length; i += 2) {
      final Token token = lexer.nextToken();
      Assert.assertEquals(expected[i], token.getType());
      Assert.assertEquals(expected[i + 1], token.getText());
    }
    Assert.assertEquals(Token.Type.EOF, lexer.nextToken().getType());
  }
}
