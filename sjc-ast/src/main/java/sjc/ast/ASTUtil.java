package sjc.ast;

import org.eclipse.jdt.core.dom.CompilationUnit;

import sjc.parser.ParserUtil;

public class ASTUtil {
  public static CompilationUnit ast(final String filename) throws Exception {
    return StaticJavaASTBuilder.ast(ParserUtil.parse(filename));
  }
}
