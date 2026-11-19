package sjc.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import sjc.ast.ASTUtil;
import sjc.llgen.LLVMIRGenerator;
import sjc.llgen.LLVMIRModule;
import sjc.symboltable.SymbolTable;
import sjc.symboltable.SymbolTableBuilder;
import sjc.type.TypeFactory;
import sjc.type.checker.TypeChecker;
import sjc.type.checker.TypeTable;

public class LLVMIRGeneratorTest {
  private static final File RESOURCES = new File("sjc-ll-gen/src/test/resources");
  private static final File TYPE_CHECK_RESOURCES =
      new File("sjc-typechecker/src/test/resources");
  private static final Path OUTPUT = Paths.get("sjc-ll-gen/output");
  private static final Path RUNTIME = Paths.get("sjc-ll-gen/runtime/sjc_runtime.c");

  private LLVMIRModule generate(final File resources, final String filename)
      throws Exception {
    final CompilationUnit cu = ASTUtil.ast(
        new File(resources, filename).getPath());
    final SymbolTable symbols = SymbolTableBuilder.build(cu);
    final TypeTable types = TypeChecker.check(
        new TypeFactory(), cu, symbols);
    return LLVMIRGenerator.generateModule(cu, symbols, types);
  }

  private Path generateFile(final String filename) throws Exception {
    return generateFile(RESOURCES, filename);
  }

  private Path generateFile(final File resources, final String filename)
      throws Exception {
    final LLVMIRModule module = generate(resources, filename);
    final Path source = OUTPUT.resolve(filename.replaceFirst("\\.java$", ".ll"));
    Files.write(source, module.llvmIR.getBytes(StandardCharsets.UTF_8));
    return source;
  }

  private static boolean clangIsAvailable() throws Exception {
    try {
      final Process probe = new ProcessBuilder("clang", "--version")
          .redirectErrorStream(true).redirectOutput(ProcessBuilder.Redirect.DISCARD)
          .start();
      return probe.waitFor() == 0;
    } catch (IOException e) {
      return false;
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUnsupportedStatementIsRejected() throws Exception {
    final CompilationUnit cu = ASTUtil.ast(
        new File(RESOURCES, "BasicTypes.java").getPath());
    final SymbolTable symbols = SymbolTableBuilder.build(cu);
    final TypeTable types = TypeChecker.check(new TypeFactory(), cu, symbols);
    final TypeDeclaration declaration = (TypeDeclaration) cu.types().get(0);
    final MethodDeclaration method = declaration.getMethods()[0];
    method.getBody().statements().add(cu.getAST().newEmptyStatement());
    final LLVMIRGenerator.Error error = Assert.assertThrows(
        LLVMIRGenerator.Error.class,
        () -> LLVMIRGenerator.generateModule(cu, symbols, types));
    Assert.assertTrue(error.getMessage().contains("Unsupported statement: EmptyStatement"));
  }

  private static Path executablePath(final String name) {
    final boolean windows = System.getProperty("os.name")
        .toLowerCase(java.util.Locale.ROOT).startsWith("windows");
    return OUTPUT.resolve(name + (windows ? ".exe" : ""));
  }

  private static void assertClangAccepts(final Path source) throws Exception {
    final Path object = OUTPUT.resolve(source.getFileName().toString()
        .replaceFirst("\\.ll$", ".o"));
    final Process compile = new ProcessBuilder("clang", "-c", source.toString(),
        "-o", object.toString()).inheritIO().start();
    Assert.assertEquals("clang rejected generated LLVM IR: " + source, 0,
        compile.waitFor());
  }

  private void testPass(final String filename) throws Exception {
    testPass(filename, null);
  }

  private void testPass(final String filename, final String expectedOutput)
      throws Exception {
    Files.createDirectories(OUTPUT);
    final Path source = generateFile(filename);
    Assert.assertTrue("empty LLVM IR generated for " + filename,
        Files.size(source) > 0);
    if (clangIsAvailable()) {
      assertClangAccepts(source);
    }
    if (expectedOutput == null) {
      return;
    }

    Assume.assumeTrue("clang is needed for execution validation",
        clangIsAvailable());
    final String programName = filename.replaceFirst("\\.java$", "");
    final Path executable = executablePath(programName);
    final Process compile = new ProcessBuilder("clang", source.toString(),
        RUNTIME.toString(), "-o", executable.toString()).inheritIO().start();
    Assert.assertEquals("clang rejected generated LLVM IR", 0,
        compile.waitFor());
    final Process run = new ProcessBuilder(executable.toString())
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start();
    final String output = new String(run.getInputStream().readAllBytes(),
        StandardCharsets.UTF_8);
    Assert.assertEquals("generated program failed", 0, run.waitFor());
    Assert.assertEquals("unexpected output from " + filename, expectedOutput,
        output.replace("\r\n", "\n"));
  }

  @Test
  public void testAEEmptyTest() throws Exception {
    testPass("AEEmptyTest.java", "1\n2\ntrue\nfalse\n");
  }

  @Test
  public void testArrayAccessVariable() throws Exception {
    testPass("ArrayAccessVariable.java", "0\n1\n0\n");
  }

  @Test
  public void testArrayCreation() throws Exception {
    testPass("ArrayCreation.java", "2\ntrue\ntrue\n");
  }

  @Test
  public void testArrayCreation2() throws Exception {
    testPass("ArrayCreation2.java", "10\n1\n5\n");
  }

  @Test
  public void testArrayIndex() throws Exception {
    testPass("ArrayIndex.java", "0\n1\n0\n");
  }

  @Test
  public void testArrayIndexConstant() throws Exception {
    testPass("ArrayIndexConstant.java", "1\n2\n3\n");
  }

  @Test
  public void testAssignNullToObject() throws Exception {
    testPass("AssignNullToObject.java");
  }

  @Test
  public void testBasicTypes() throws Exception {
    testPass("BasicTypes.java", "0\ntrue\n");
  }

  @Test
  public void testBinaryOps() throws Exception {
    testPass("BinaryOps.java");
  }

  @Test
  public void testBooleanAnd() throws Exception {
    testPass("BooleanAnd.java");
  }

  @Test
  public void testBooleanLiteral() throws Exception {
    testPass("BooleanLiteral.java");
  }

  @Test
  public void testBooleanNot() throws Exception {
    testPass("BooleanNot.java");
  }

  @Test
  public void testBooleanOr() throws Exception {
    testPass("BooleanOr.java");
  }

  @Test
  public void testConditionalFalse() throws Exception {
    testPass("ConditionalFalse.java");
  }

  @Test
  public void testConditionalTrue() throws Exception {
    testPass("ConditionalTrue.java");
  }

  @Test
  public void testConditionalWithNull() throws Exception {
    testPass("ConditionalWithNull.java");
  }

  @Test
  public void testCreateIntArrayWithIntAndIntLiteralInitializers() throws Exception {
    testPass("CreateIntArrayWithIntAndIntLiteralInitializers.java", "0\n1\n");
  }

  @Test
  public void testDoWhile() throws Exception {
    testPass("DoWhile.java");
  }

  @Test
  public void testDoWhileWithBooleanArrayAccessCondition() throws Exception {
    testPass("DoWhileWithBooleanArrayAccessCondition.java");
  }

  @Test
  public void testFieldAccess() throws Exception {
    testPass("FieldAccess.java");
  }

  @Test
  public void testFor() throws Exception {
    testPass("For.java");
  }

  @Test
  public void testForBooleanArrayAccessConditional() throws Exception {
    testPass("ForBooleanArrayAccessConditional.java");
  }

  @Test
  public void testForBooleanMemberAccessConditional() throws Exception {
    testPass("ForBooleanMemberAccessConditional.java");
  }

  @Test
  public void testForCondOnly() throws Exception {
    testPass("ForCondOnly.java");
  }

  @Test
  public void testForEmpty() throws Exception {
    testPass("ForEmpty.java");
  }

  @Test
  public void testForFull() throws Exception {
    testPass("ForFull.java");
  }

  @Test
  public void testForIncOrDecOnly() throws Exception {
    testPass("ForIncOrDecOnly.java");
  }

  @Test
  public void testForInitOnly() throws Exception {
    testPass("ForInitOnly.java");
  }

  @Test
  public void testForLoop() throws Exception {
    testPass("ForLoop.java");
  }

  @Test
  public void testForMissingCond() throws Exception {
    testPass("ForMissingCond.java");
  }

  @Test
  public void testForMissingIncOrDec() throws Exception {
    testPass("ForMissingIncOrDec.java");
  }

  @Test
  public void testForMissingInit() throws Exception {
    testPass("ForMissingInit.java");
  }

  @Test
  public void testForMultipleIncOrDecOnly() throws Exception {
    testPass("ForMultipleIncOrDecOnly.java");
  }

  @Test
  public void testForMultipleInitAndIncOrDec() throws Exception {
    testPass("ForMultipleInitAndIncOrDec.java");
  }

  @Test
  public void testForMultipleInitOnly() throws Exception {
    testPass("ForMultipleInitOnly.java");
  }

  @Test
  public void testForwardClassTest() throws Exception {
    testPass("ForwardClassTest.java");
  }

  @Test
  public void testForwardClassRefs() throws Exception {
    testPass("ForwardClassRefs.java");
  }

  @Test
  public void testIf() throws Exception {
    testPass("If.java");
  }

  @Test
  public void testIfFalseEmpty() throws Exception {
    testPass("IfFalseEmpty.java");
  }

  @Test
  public void testIfFalseSingle() throws Exception {
    testPass("IfFalseSingle.java");
  }

  @Test
  public void testIfFalseSingleElseEmpty() throws Exception {
    testPass("IfFalseSingleElseEmpty.java");
  }

  @Test
  public void testIfFalseSingleElseMultiple() throws Exception {
    testPass("IfFalseSingleElseMultiple.java");
  }

  @Test
  public void testIfFalseSingleElseSingle() throws Exception {
    testPass("IfFalseSingleElseSingle.java");
  }

  @Test
  public void testIfTrueEmpty() throws Exception {
    testPass("IfTrueEmpty.java");
  }

  @Test
  public void testIfTrueEmptyElseEmpty() throws Exception {
    testPass("IfTrueEmptyElseEmpty.java");
  }

  @Test
  public void testIfTrueMultiple() throws Exception {
    testPass("IfTrueMultiple.java");
  }

  @Test
  public void testIfTrueSingle() throws Exception {
    testPass("IfTrueSingle.java");
  }

  @Test
  public void testIfTrueSingleElseEmpty() throws Exception {
    testPass("IfTrueSingleElseEmpty.java");
  }

  @Test
  public void testIfTrueSingleElseSingle() throws Exception {
    testPass("IfTrueSingleElseSingle.java");
  }

  @Test
  public void testIncIntArrayAccess() throws Exception {
    testPass("IncIntArrayAccess.java");
  }

  @Test
  public void testIntAdd() throws Exception {
    testPass("IntAdd.java");
  }

  @Test
  public void testIntComplement() throws Exception {
    testPass("IntComplement.java");
  }

  @Test
  public void testIntDivide() throws Exception {
    testPass("IntDivide.java");
  }

  @Test
  public void testIntEqual() throws Exception {
    testPass("IntEqual.java");
  }

  @Test
  public void testIntGreaterThan() throws Exception {
    testPass("IntGreaterThan.java");
  }

  @Test
  public void testIntGreaterThanOrEqual() throws Exception {
    testPass("IntGreaterThanOrEqual.java");
  }

  @Test
  public void testIntLessThan() throws Exception {
    testPass("IntLessThan.java");
  }

  @Test
  public void testIntLessThanOrEqual() throws Exception {
    testPass("IntLessThanOrEqual.java");
  }

  @Test
  public void testIntMultiply() throws Exception {
    testPass("IntMultiply.java");
  }

  @Test
  public void testIntNegate() throws Exception {
    testPass("IntNegate.java");
  }

  @Test
  public void testIntNotEqual() throws Exception {
    testPass("IntNotEqual.java");
  }

  @Test
  public void testIntPlus() throws Exception {
    testPass("IntPlus.java");
  }

  @Test
  public void testIntPostDecrement() throws Exception {
    testPass("IntPostDecrement.java");
  }

  @Test
  public void testIntPostIncrement() throws Exception {
    testPass("IntPostIncrement.java");
  }

  @Test
  public void testIntRemainder() throws Exception {
    testPass("IntRemainder.java");
  }

  @Test
  public void testIntShiftLeft() throws Exception {
    testPass("IntShiftLeft.java");
  }

  @Test
  public void testIntShiftRight() throws Exception {
    testPass("IntShiftRight.java");
  }

  @Test
  public void testIntSubtract() throws Exception {
    testPass("IntSubtract.java");
  }

  @Test
  public void testIntUnsignedShiftRight() throws Exception {
    testPass("IntUnsignedShiftRight.java");
  }

  @Test
  public void testNewBasic() throws Exception {
    testPass("NewBasic.java");
  }

  @Test
  public void testNewID() throws Exception {
    testPass("NewID.java");
  }

  @Test
  public void testNullArgumentForIntArrayParameter() throws Exception {
    testPass("NullArgumentForIntArrayParameter.java");
  }

  @Test
  public void testNullEqualsNull() throws Exception {
    testPass("NullEqualsNull.java");
  }

  @Test
  public void testObjectAEqualsReturnedObjectA() throws Exception {
    testPass("ObjectAEqualsReturnedObjectA.java");
  }

  @Test
  public void testParens() throws Exception {
    testPass("Parens.java");
  }

  @Test
  public void testPower() throws Exception {
    testPass("Power.java");
  }

  @Test
  public void testProcedureCall() throws Exception {
    testPass("ProcedureCall.java");
  }

  @Test
  public void testReturnNullFromIntArrayMethod() throws Exception {
    testPass("ReturnNullFromIntArrayMethod.java");
  }

  @Test
  public void testSAExample() throws Exception {
    testPass("SAExample.java");
  }

  @Test
  public void testSymbolTableTest() throws Exception {
    testPass("SymbolTableTest.java");
  }

  @Test
  public void testSyntaxTorture() throws Exception {
    testPass("SyntaxTorture.java");
  }

  @Test
  public void testTypesArray() throws Exception {
    testPass("TypesArray.java");
  }

  @Test
  public void testTypesBasic() throws Exception {
    testPass("TypesBasic.java");
  }

  @Test
  public void testTypesID() throws Exception {
    testPass("TypesID.java");
  }

  @Test
  public void testUnaryOps() throws Exception {
    testPass("UnaryOps.java");
  }

  @Test
  public void testWhile() throws Exception {
    testPass("While.java");
  }

  @Test
  public void testWhileInitRD() throws Exception {
    testPass("WhileInitRD.java");
  }

  @Test
  public void testOriginalTypeCheckingExamples() throws Exception {
    final String[] filenames = {
      "AEEmptyTest.java", "ArrayAccessVariable.java", "ArrayCreation.java",
      "ArrayCreation2.java", "ArrayIndex.java", "ArrayIndexConstant.java",
      "BasicTypes.java", "CreateIntArrayWithIntAndIntLiteralInitializers.java",
      "If.java", "While.java"
    };
    Files.createDirectories(OUTPUT);
    for (final String filename : filenames) {
      final Path source = generateFile(TYPE_CHECK_RESOURCES, filename);
      Assert.assertTrue("empty LLVM IR generated for " + filename,
          Files.size(source) > 0);
      if (clangIsAvailable()) {
        assertClangAccepts(source);
      }
    }
  }

  @Test
  public void testTypeCheckingFailures() throws Exception {
    final String[] filenames = {
      "TypeCheckFailAssignAnonymousBooleanArrayToBoolean.java",
      "TypeCheckFailAssignAnonymousBooleanArrayToInt.java",
      "TypeCheckFailAssignAnonymousBooleanArrayToIntArray.java",
      "TypeCheckFailAssignAnonymousBooleanArrayToObject.java",
      "TypeCheckFailAssignAnonymousBooleanArrayToObjectArray.java",
      "TypeCheckFailAssignAnonymousIntArrayToBoolean.java",
      "TypeCheckFailAssignAnonymousIntArrayToBooleanArray.java",
      "TypeCheckFailAssignAnonymousIntArrayToInt.java",
      "TypeCheckFailAssignAnonymousIntArrayToObject.java",
      "TypeCheckFailAssignAnonymousIntArrayToObjectArray.java",
      "TypeCheckFailAssignAnonymousObjectAArrayToObjectA.java",
      "TypeCheckFailAssignAnonymousObjectAArrayToObjectB.java",
      "TypeCheckFailAssignAnonymousObjectAArrayToObjectBArray.java",
      "TypeCheckFailAssignAnonymousObjectArrayToBoolean.java",
      "TypeCheckFailAssignAnonymousObjectArrayToBooleanArray.java",
      "TypeCheckFailAssignAnonymousObjectArrayToInt.java",
      "TypeCheckFailAssignAnonymousObjectArrayToIntArray.java",
      "TypeCheckFailAssignAnonymousObjectAToObjectB.java",
      "TypeCheckFailAssignAnonymousObjectAToObjectBArray.java",
      "TypeCheckFailAssignAnonymousObjectToBoolean.java",
      "TypeCheckFailAssignAnonymousObjectToBooleanArray.java",
      "TypeCheckFailAssignAnonymousObjectToInt.java",
      "TypeCheckFailAssignAnonymousObjectToIntArray.java",
      "TypeCheckFailAssignBooleanToInt.java",
      "TypeCheckFailAssignBooleanToIntArray.java",
      "TypeCheckFailAssignBooleanToObject.java",
      "TypeCheckFailAssignBooleanToObjectArray.java",
      "TypeCheckFailAssignFalseToInt.java",
      "TypeCheckFailAssignFalseToIntArray.java",
      "TypeCheckFailAssignFalseToObject.java",
      "TypeCheckFailAssignFalseToObjectArray.java",
      "TypeCheckFailAssignNamedBooleanArrayToBoolean.java",
      "TypeCheckFailAssignNamedBooleanArrayToInt.java",
      "TypeCheckFailAssignNamedBooleanArrayToIntArray.java",
      "TypeCheckFailAssignNamedBooleanArrayToObject.java",
      "TypeCheckFailAssignNamedBooleanArrayToObjectArray.java",
      "TypeCheckFailAssignNamedIntArrayToBoolean.java",
      "TypeCheckFailAssignNamedIntArrayToBooleanArray.java",
      "TypeCheckFailAssignNamedIntArrayToInt.java",
      "TypeCheckFailAssignNamedIntArrayToObject.java",
      "TypeCheckFailAssignNamedIntArrayToObjectArray.java",
      "TypeCheckFailAssignNamedObjectAArrayToObjectA.java",
      "TypeCheckFailAssignNamedObjectAArrayToObjectB.java",
      "TypeCheckFailAssignNamedObjectAArrayToObjectBArray.java",
      "TypeCheckFailAssignNamedObjectArrayToBoolean.java",
      "TypeCheckFailAssignNamedObjectArrayToBooleanArray.java",
      "TypeCheckFailAssignNamedObjectArrayToInt.java",
      "TypeCheckFailAssignNamedObjectArrayToIntArray.java",
      "TypeCheckFailAssignNamedObjectAToObjectB.java",
      "TypeCheckFailAssignNamedObjectAToObjectBArray.java",
      "TypeCheckFailAssignNamedObjectToBoolean.java",
      "TypeCheckFailAssignNamedObjectToBooleanArray.java",
      "TypeCheckFailAssignNamedObjectToInt.java",
      "TypeCheckFailAssignNamedObjectToIntArray.java",
      "TypeCheckFailAssignTrueToInt.java",
      "TypeCheckFailAssignTrueToIntArray.java",
      "TypeCheckFailAssignTrueToObject.java",
      "TypeCheckFailAssignTrueToObjectArray.java",
      "TypeCheckFailBooleanArrayWithBooleanAccess.java",
      "TypeCheckFailComplementBadExpression.java",
      "TypeCheckFailComplementBoolean.java",
      "TypeCheckFailComplementNull.java",
      "TypeCheckFailComplementTrue.java",
      "TypeCheckFailConditionalAssignIntToBool.java",
      "TypeCheckFailConditionalAssignObjectAToObjectAArray.java",
      "TypeCheckFailCreateArrayWithBooleanLength.java",
      "TypeCheckFailCreateArrayWithFalseLength.java",
      "TypeCheckFailCreateIntArrayForBooleanArray.java",
      "TypeCheckFailCreateIntArrayForInt.java",
      "TypeCheckFailCreateIntArrayWithBooleanInitializer.java",
      "TypeCheckFailCreateIntArrayWithIntAndBooleanInitializers.java",
      "TypeCheckFailCreateObjectAForObjectAArray.java",
      "TypeCheckFailCreateObjectBForObjectA.java",
      "TypeCheckFailDoWhileWithBooleanArrayCondition.java",
      "TypeCheckFailForBooleanArrayConditional.java",
      "TypeCheckFailForIntCondtional.java",
      "TypeCheckFailIncBoolean.java",
      "TypeCheckFailIncIntArray.java",
      "TypeCheckFailIntArgumentForBooleanParameter.java",
      "TypeCheckFailIntConditional.java",
      "TypeCheckFailIntEqualsBoolean.java",
      "TypeCheckFailIntEqualsIntArray.java",
      "TypeCheckFailIntLiteralConditional.java",
      "TypeCheckFailIntPlusBadExpression.java",
      "TypeCheckFailIntPlusObject.java",
      "TypeCheckFailNegateBadExpression.java",
      "TypeCheckFailNegateBoolean.java",
      "TypeCheckFailNegateIntArray.java",
      "TypeCheckFailNegateObject.java",
      "TypeCheckFailNullEqualsVoid.java",
      "TypeCheckFailObjectAEqualsReturnedObjectB.java",
      "TypeCheckFailObjectEqualsBoolean.java",
      "TypeCheckFailReturnIntFromBooleanMethod.java",
      "TypeCheckFailReturnIntLiteralFromVoidMethod.java",
      "TypeCheckFailTooFewMethodArguments.java",
      "TypeCheckFailTooManyMethodArguments.java",
      "TypeCheckFailUndefinedClass.java",
    };
    for (final String filename : filenames) {
      final TypeChecker.Error error = Assert.assertThrows(
          "Expected type checking to reject " + filename,
          TypeChecker.Error.class, () -> generate(TYPE_CHECK_RESOURCES, filename));
      final String expected = new String(Files.readAllBytes(
          new File(TYPE_CHECK_RESOURCES, filename + ".exp").toPath()),
          StandardCharsets.UTF_8).replace("\r\n", "\n").trim();
      Assert.assertEquals("Unexpected diagnostic for " + filename, expected,
          error.getMessage().replace("\r\n", "\n").trim());
    }
  }

  @Test
  public void testQueue() throws Exception {
    Assume.assumeTrue("clang is needed for execution validation",
        clangIsAvailable());

    Files.createDirectories(OUTPUT);
    final Path source = generateFile("Queue.java");
    final Path executable = executablePath("Queue");
    final Process compile = new ProcessBuilder("clang", source.toString(),
        RUNTIME.toString(), "-o", executable.toString()).inheritIO().start();
    Assert.assertEquals("clang rejected generated LLVM IR", 0,
        compile.waitFor());
    final Process run = new ProcessBuilder(executable.toString())
        .inheritIO().start();
    Assert.assertEquals("generated program failed", 0, run.waitFor());
  }

  @Test
  public void testIntegerArguments() throws Exception {
    Assume.assumeTrue("clang is needed for execution validation",
        clangIsAvailable());
    Files.createDirectories(OUTPUT);
    final Path source = generateFile("IntegerArguments.java");
    final Path executable = executablePath("IntegerArguments");
    final Process compile = new ProcessBuilder("clang", source.toString(),
        RUNTIME.toString(), "-o", executable.toString()).inheritIO().start();
    Assert.assertEquals("clang rejected generated LLVM IR", 0, compile.waitFor());
    final Process run = new ProcessBuilder(executable.toString(),
        "-2147483648", "2147483647")
        .redirectError(ProcessBuilder.Redirect.INHERIT).start();
    final String output = new String(run.getInputStream().readAllBytes(),
        StandardCharsets.UTF_8).replace("\r\n", "\n");
    Assert.assertEquals("generated program failed", 0, run.waitFor());
    Assert.assertEquals("-2147483648\n2147483647\n", output);
  }

  @Test
  public void testFactorial() throws Exception {
    Assume.assumeTrue("clang is needed for execution validation",
        clangIsAvailable());

    Files.createDirectories(OUTPUT);
    final Path source = generateFile("Factorial.java");
    final Path executable = executablePath("Factorial");
    final Process compile = new ProcessBuilder("clang", source.toString(),
        RUNTIME.toString(), "-o", executable.toString()).inheritIO().start();
    Assert.assertEquals("clang rejected generated LLVM IR", 0,
        compile.waitFor());
    final Process run = new ProcessBuilder(executable.toString(), "5")
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start();
    final String output = new String(run.getInputStream().readAllBytes(),
        StandardCharsets.UTF_8).trim();
    Assert.assertEquals("generated program failed", 0, run.waitFor());
    Assert.assertEquals("120", output);
  }

}
