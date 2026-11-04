package sjc.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import sjc.ast.ASTUtil;
import sjc.symboltable.SymbolTable;
import sjc.symboltable.SymbolTableBuilder;
import sjc.util.Diff;
import sjc.util.DiffPrint;
import sjc.util.Util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/** Tests symbol-table construction using the type checker's subject programs. */
public class SymbolTableBuilderTest {
  private static boolean diff(final Object[] resultStrings,
      final Object[] expectedStrings) {
    final Diff diff = new Diff(resultStrings, expectedStrings);
    final Diff.change script = diff.diff_2(false);
    final DiffPrint.Base p = new DiffPrint.UnifiedPrint(resultStrings,
        expectedStrings);
    if (script == null) {
      System.out.println("OK");
      return true;
    } else {
      p.setOutput(new PrintWriter(System.out));
      p.print_script(script);
      System.err.flush();
      return false;
    }
  }

  private static Object[] getExpectedSymbolStrings(final String filePath)
      throws Exception {
    final ArrayList<Object> list = new ArrayList<>();
    final BufferedReader br = new BufferedReader(new FileReader(filePath + ".exp"));
    String line = br.readLine();
    while (line != null && !line.isEmpty()) {
      list.add(line);
      line = br.readLine();
    }
    br.close();
    return list.toArray();
  }

  private static Object[] getLines(final String value) throws Exception {
    final ArrayList<Object> list = new ArrayList<>();
    final BufferedReader br = new BufferedReader(new java.io.StringReader(value));
    String line = br.readLine();
    while (line != null) {
      list.add(line);
      line = br.readLine();
    }
    br.close();
    return list.toArray();
  }

  private static void testBuild(final String filename) throws Exception {
    final String path = Util.getResource(SymbolTableBuilderTest.class,
        "../..", filename);
    assertNotNull(SymbolTableBuilder.build(ASTUtil.ast(path)));
  }

  private static void testPass(final String filename) {
    System.out.println("*** Begin testing: " + filename);
    try {
      final String filePath = Util.getResource(SymbolTableBuilderTest.class,
          "../..", filename);
      final CompilationUnit cu = ASTUtil.ast(filePath);
      final SymbolTable symbolTable = SymbolTableBuilder.build(cu);
      final boolean matches = diff(getLines(symbolTable.toString()),
          getExpectedSymbolStrings(filePath));
      if (!matches) {
        fail();
      }
    } catch (final Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testAEEmptyTest() throws Exception {
    testPass("AEEmptyTest.java");
  }

  @Test
  public void testArrayAccessVariable() throws Exception {
    testPass("ArrayAccessVariable.java");
  }

  @Test
  public void testArrayCreation() throws Exception {
    testPass("ArrayCreation.java");
  }

  @Test
  public void testArrayCreation2() throws Exception {
    testPass("ArrayCreation2.java");
  }

  @Test
  public void testArrayIndex() throws Exception {
    testPass("ArrayIndex.java");
  }

  @Test
  public void testArrayIndexConstant() throws Exception {
    testPass("ArrayIndexConstant.java");
  }

  @Test
  public void testAssignNullToObject() throws Exception {
    testPass("AssignNullToObject.java");
  }

  @Test
  public void testBasicTypes() throws Exception {
    testPass("BasicTypes.java");
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
    testPass("CreateIntArrayWithIntAndIntLiteralInitializers.java");
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
  public void testFactorial() throws Exception {
    testPass("Factorial.java");
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
  public void testForwardClassRefs() throws Exception {
    testPass("ForwardClassRefs.java");
  }

  @Test
  public void testForwardClassTest() throws Exception {
    testPass("ForwardClassTest.java");
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
  public void testQueue() throws Exception {
    testPass("Queue.java");
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
  public void testTypeCheckFailAssignAnonymousBooleanArrayToBoolean() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousBooleanArrayToBoolean.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousBooleanArrayToInt() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousBooleanArrayToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousBooleanArrayToIntArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousBooleanArrayToIntArray.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousBooleanArrayToObject() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousBooleanArrayToObject.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousBooleanArrayToObjectArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousBooleanArrayToObjectArray.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousIntArrayToBoolean() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousIntArrayToBoolean.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousIntArrayToBooleanArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousIntArrayToBooleanArray.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousIntArrayToInt() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousIntArrayToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousIntArrayToObject() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousIntArrayToObject.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousIntArrayToObjectArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousIntArrayToObjectArray.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectAArrayToObjectA() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectAArrayToObjectA.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectAArrayToObjectB() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectAArrayToObjectB.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectAArrayToObjectBArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectAArrayToObjectBArray.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectArrayToBoolean() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectArrayToBoolean.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectArrayToBooleanArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectArrayToBooleanArray.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectArrayToInt() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectArrayToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectArrayToIntArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectArrayToIntArray.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectAToObjectB() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectAToObjectB.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectAToObjectBArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectAToObjectBArray.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectToBoolean() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectToBoolean.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectToBooleanArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectToBooleanArray.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectToInt() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignAnonymousObjectToIntArray() throws Exception {
    testBuild("TypeCheckFailAssignAnonymousObjectToIntArray.java");
  }

  @Test
  public void testTypeCheckFailAssignBooleanToInt() throws Exception {
    testBuild("TypeCheckFailAssignBooleanToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignBooleanToIntArray() throws Exception {
    testBuild("TypeCheckFailAssignBooleanToIntArray.java");
  }

  @Test
  public void testTypeCheckFailAssignBooleanToObject() throws Exception {
    testBuild("TypeCheckFailAssignBooleanToObject.java");
  }

  @Test
  public void testTypeCheckFailAssignBooleanToObjectArray() throws Exception {
    testBuild("TypeCheckFailAssignBooleanToObjectArray.java");
  }

  @Test
  public void testTypeCheckFailAssignFalseToInt() throws Exception {
    testBuild("TypeCheckFailAssignFalseToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignFalseToIntArray() throws Exception {
    testBuild("TypeCheckFailAssignFalseToIntArray.java");
  }

  @Test
  public void testTypeCheckFailAssignFalseToObject() throws Exception {
    testBuild("TypeCheckFailAssignFalseToObject.java");
  }

  @Test
  public void testTypeCheckFailAssignFalseToObjectArray() throws Exception {
    testBuild("TypeCheckFailAssignFalseToObjectArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedBooleanArrayToBoolean() throws Exception {
    testBuild("TypeCheckFailAssignNamedBooleanArrayToBoolean.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedBooleanArrayToInt() throws Exception {
    testBuild("TypeCheckFailAssignNamedBooleanArrayToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedBooleanArrayToIntArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedBooleanArrayToIntArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedBooleanArrayToObject() throws Exception {
    testBuild("TypeCheckFailAssignNamedBooleanArrayToObject.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedBooleanArrayToObjectArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedBooleanArrayToObjectArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedIntArrayToBoolean() throws Exception {
    testBuild("TypeCheckFailAssignNamedIntArrayToBoolean.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedIntArrayToBooleanArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedIntArrayToBooleanArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedIntArrayToInt() throws Exception {
    testBuild("TypeCheckFailAssignNamedIntArrayToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedIntArrayToObject() throws Exception {
    testBuild("TypeCheckFailAssignNamedIntArrayToObject.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedIntArrayToObjectArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedIntArrayToObjectArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectAArrayToObjectA() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectAArrayToObjectA.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectAArrayToObjectB() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectAArrayToObjectB.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectAArrayToObjectBArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectAArrayToObjectBArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectArrayToBoolean() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectArrayToBoolean.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectArrayToBooleanArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectArrayToBooleanArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectArrayToInt() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectArrayToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectArrayToIntArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectArrayToIntArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectAToObjectB() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectAToObjectB.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectAToObjectBArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectAToObjectBArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectToBoolean() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectToBoolean.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectToBooleanArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectToBooleanArray.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectToInt() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignNamedObjectToIntArray() throws Exception {
    testBuild("TypeCheckFailAssignNamedObjectToIntArray.java");
  }

  @Test
  public void testTypeCheckFailAssignTrueToInt() throws Exception {
    testBuild("TypeCheckFailAssignTrueToInt.java");
  }

  @Test
  public void testTypeCheckFailAssignTrueToIntArray() throws Exception {
    testBuild("TypeCheckFailAssignTrueToIntArray.java");
  }

  @Test
  public void testTypeCheckFailAssignTrueToObject() throws Exception {
    testBuild("TypeCheckFailAssignTrueToObject.java");
  }

  @Test
  public void testTypeCheckFailAssignTrueToObjectArray() throws Exception {
    testBuild("TypeCheckFailAssignTrueToObjectArray.java");
  }

  @Test
  public void testTypeCheckFailBooleanArrayWithBooleanAccess() throws Exception {
    testBuild("TypeCheckFailBooleanArrayWithBooleanAccess.java");
  }

  @Test
  public void testTypeCheckFailComplementBadExpression() throws Exception {
    testBuild("TypeCheckFailComplementBadExpression.java");
  }

  @Test
  public void testTypeCheckFailComplementBoolean() throws Exception {
    testBuild("TypeCheckFailComplementBoolean.java");
  }

  @Test
  public void testTypeCheckFailComplementNull() throws Exception {
    testBuild("TypeCheckFailComplementNull.java");
  }

  @Test
  public void testTypeCheckFailComplementTrue() throws Exception {
    testBuild("TypeCheckFailComplementTrue.java");
  }

  @Test
  public void testTypeCheckFailConditionalAssignIntToBool() throws Exception {
    testBuild("TypeCheckFailConditionalAssignIntToBool.java");
  }

  @Test
  public void testTypeCheckFailConditionalAssignObjectAToObjectAArray() throws Exception {
    testBuild("TypeCheckFailConditionalAssignObjectAToObjectAArray.java");
  }

  @Test
  public void testTypeCheckFailCreateArrayWithBooleanLength() throws Exception {
    testBuild("TypeCheckFailCreateArrayWithBooleanLength.java");
  }

  @Test
  public void testTypeCheckFailCreateArrayWithFalseLength() throws Exception {
    testBuild("TypeCheckFailCreateArrayWithFalseLength.java");
  }

  @Test
  public void testTypeCheckFailCreateIntArrayForBooleanArray() throws Exception {
    testBuild("TypeCheckFailCreateIntArrayForBooleanArray.java");
  }

  @Test
  public void testTypeCheckFailCreateIntArrayForInt() throws Exception {
    testBuild("TypeCheckFailCreateIntArrayForInt.java");
  }

  @Test
  public void testTypeCheckFailCreateIntArrayWithBooleanInitializer() throws Exception {
    testBuild("TypeCheckFailCreateIntArrayWithBooleanInitializer.java");
  }

  @Test
  public void testTypeCheckFailCreateIntArrayWithIntAndBooleanInitializers() throws Exception {
    testBuild("TypeCheckFailCreateIntArrayWithIntAndBooleanInitializers.java");
  }

  @Test
  public void testTypeCheckFailCreateObjectAForObjectAArray() throws Exception {
    testBuild("TypeCheckFailCreateObjectAForObjectAArray.java");
  }

  @Test
  public void testTypeCheckFailCreateObjectBForObjectA() throws Exception {
    testBuild("TypeCheckFailCreateObjectBForObjectA.java");
  }

  @Test
  public void testTypeCheckFailDoWhileWithBooleanArrayCondition() throws Exception {
    testBuild("TypeCheckFailDoWhileWithBooleanArrayCondition.java");
  }

  @Test
  public void testTypeCheckFailForBooleanArrayConditional() throws Exception {
    testBuild("TypeCheckFailForBooleanArrayConditional.java");
  }

  @Test
  public void testTypeCheckFailForIntCondtional() throws Exception {
    testBuild("TypeCheckFailForIntCondtional.java");
  }

  @Test
  public void testTypeCheckFailIncBoolean() throws Exception {
    testBuild("TypeCheckFailIncBoolean.java");
  }

  @Test
  public void testTypeCheckFailIncIntArray() throws Exception {
    testBuild("TypeCheckFailIncIntArray.java");
  }

  @Test
  public void testTypeCheckFailIntArgumentForBooleanParameter() throws Exception {
    testBuild("TypeCheckFailIntArgumentForBooleanParameter.java");
  }

  @Test
  public void testTypeCheckFailIntConditional() throws Exception {
    testBuild("TypeCheckFailIntConditional.java");
  }

  @Test
  public void testTypeCheckFailIntEqualsBoolean() throws Exception {
    testBuild("TypeCheckFailIntEqualsBoolean.java");
  }

  @Test
  public void testTypeCheckFailIntEqualsIntArray() throws Exception {
    testBuild("TypeCheckFailIntEqualsIntArray.java");
  }

  @Test
  public void testTypeCheckFailIntLiteralConditional() throws Exception {
    testBuild("TypeCheckFailIntLiteralConditional.java");
  }

  @Test
  public void testTypeCheckFailIntPlusBadExpression() throws Exception {
    testBuild("TypeCheckFailIntPlusBadExpression.java");
  }

  @Test
  public void testTypeCheckFailIntPlusObject() throws Exception {
    testBuild("TypeCheckFailIntPlusObject.java");
  }

  @Test
  public void testTypeCheckFailNegateBadExpression() throws Exception {
    testBuild("TypeCheckFailNegateBadExpression.java");
  }

  @Test
  public void testTypeCheckFailNegateBoolean() throws Exception {
    testBuild("TypeCheckFailNegateBoolean.java");
  }

  @Test
  public void testTypeCheckFailNegateIntArray() throws Exception {
    testBuild("TypeCheckFailNegateIntArray.java");
  }

  @Test
  public void testTypeCheckFailNegateObject() throws Exception {
    testBuild("TypeCheckFailNegateObject.java");
  }

  @Test
  public void testTypeCheckFailNullEqualsVoid() throws Exception {
    testBuild("TypeCheckFailNullEqualsVoid.java");
  }

  @Test
  public void testTypeCheckFailObjectAEqualsReturnedObjectB() throws Exception {
    testBuild("TypeCheckFailObjectAEqualsReturnedObjectB.java");
  }

  @Test
  public void testTypeCheckFailObjectEqualsBoolean() throws Exception {
    testBuild("TypeCheckFailObjectEqualsBoolean.java");
  }

  @Test
  public void testTypeCheckFailReturnIntFromBooleanMethod() throws Exception {
    testBuild("TypeCheckFailReturnIntFromBooleanMethod.java");
  }

  @Test
  public void testTypeCheckFailReturnIntLiteralFromVoidMethod() throws Exception {
    testBuild("TypeCheckFailReturnIntLiteralFromVoidMethod.java");
  }

  @Test
  public void testTypeCheckFailTooFewMethodArguments() throws Exception {
    testBuild("TypeCheckFailTooFewMethodArguments.java");
  }

  @Test
  public void testTypeCheckFailTooManyMethodArguments() throws Exception {
    testBuild("TypeCheckFailTooManyMethodArguments.java");
  }

  @Test
  public void testTypeCheckFailUndefinedClass() throws Exception {
    testBuild("TypeCheckFailUndefinedClass.java");
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
  public void testTwoMethods() throws Exception {
    testBuild("TwoMethods.java");
  }
}
