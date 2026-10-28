package sjc.ast;

import java.util.HashMap;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import sjc.parser.StaticJavaBaseVisitor;
import sjc.parser.StaticJavaParser.AccessSuffixContext;
import sjc.parser.StaticJavaParser.ArgsContext;
import sjc.parser.StaticJavaParser.ArrayLHSContext;
import sjc.parser.StaticJavaParser.AssignContext;
import sjc.parser.StaticJavaParser.AssignStatementContext;
import sjc.parser.StaticJavaParser.AssignsContext;
import sjc.parser.StaticJavaParser.BinaryExpContext;
import sjc.parser.StaticJavaParser.BooleanTypeContext;
import sjc.parser.StaticJavaParser.ClassDefinitionContext;
import sjc.parser.StaticJavaParser.CompilationUnitContext;
import sjc.parser.StaticJavaParser.CondExpContext;
import sjc.parser.StaticJavaParser.DoWhileStatementContext;
import sjc.parser.StaticJavaParser.ExpContext;
import sjc.parser.StaticJavaParser.FalseLiteralContext;
import sjc.parser.StaticJavaParser.FieldDeclarationContext;
import sjc.parser.StaticJavaParser.FieldLHSContext;
import sjc.parser.StaticJavaParser.ForStatementContext;
import sjc.parser.StaticJavaParser.IdExpContext;
import sjc.parser.StaticJavaParser.IdLHSContext;
import sjc.parser.StaticJavaParser.IfStatementContext;
import sjc.parser.StaticJavaParser.IncdecContext;
import sjc.parser.StaticJavaParser.IncdecStatementContext;
import sjc.parser.StaticJavaParser.IncdecsContext;
import sjc.parser.StaticJavaParser.IndexingSuffixContext;
import sjc.parser.StaticJavaParser.IntLiteralContext;
import sjc.parser.StaticJavaParser.IntTypeContext;
import sjc.parser.StaticJavaParser.InvokeContext;
import sjc.parser.StaticJavaParser.InvokeExpContext;
import sjc.parser.StaticJavaParser.InvokeExpStatementContext;
import sjc.parser.StaticJavaParser.LeftShiftOpContext;
import sjc.parser.StaticJavaParser.LocalDeclarationContext;
import sjc.parser.StaticJavaParser.MainMethodDeclarationContext;
import sjc.parser.StaticJavaParser.MemberDeclarationContext;
import sjc.parser.StaticJavaParser.MethodBodyContext;
import sjc.parser.StaticJavaParser.MethodDeclarationContext;
import sjc.parser.StaticJavaParser.NewArrayBasicContext;
import sjc.parser.StaticJavaParser.NewArrayBasicInitContext;
import sjc.parser.StaticJavaParser.NewArrayObjectContext;
import sjc.parser.StaticJavaParser.NewArrayObjectInitContext;
import sjc.parser.StaticJavaParser.NewObjectContext;
import sjc.parser.StaticJavaParser.NonVoidReturnTypeContext;
import sjc.parser.StaticJavaParser.NullLiteralContext;
import sjc.parser.StaticJavaParser.PSuffixContext;
import sjc.parser.StaticJavaParser.ParamContext;
import sjc.parser.StaticJavaParser.ParamsContext;
import sjc.parser.StaticJavaParser.ParenExpContext;
import sjc.parser.StaticJavaParser.PrimaryExpContext;
import sjc.parser.StaticJavaParser.PrimaryPrefixContext;
import sjc.parser.StaticJavaParser.PublicFieldDeclarationContext;
import sjc.parser.StaticJavaParser.ReturnStatementContext;
import sjc.parser.StaticJavaParser.RightShiftOpContext;
import sjc.parser.StaticJavaParser.ShiftBinaryExpContext;
import sjc.parser.StaticJavaParser.ShiftOpContext;
import sjc.parser.StaticJavaParser.SimpleClassDefinitionContext;
import sjc.parser.StaticJavaParser.StatementContext;
import sjc.parser.StaticJavaParser.TrueLiteralContext;
import sjc.parser.StaticJavaParser.TypeContext;
import sjc.parser.StaticJavaParser.UnaryExpContext;
import sjc.parser.StaticJavaParser.VoidTypeContext;
import sjc.parser.StaticJavaParser.WhileStatementContext;

/**
 * This class builds JDT AST from ANTLR Parse Tree produced by
 * StaticJava parser.
 *
 */
public class StaticJavaASTBuilder extends
        StaticJavaBaseVisitor<ASTNode> {

  static CompilationUnit ast(final CompilationUnitContext ctx) {
    final StaticJavaASTBuilder builder = new StaticJavaASTBuilder();
    CompilationUnit ast = builder.<CompilationUnit> build(ctx);
    return builder.<CompilationUnit> build(ctx);
  }

  final static HashMap<String, InfixExpression.Operator> binopMap;

  final static HashMap<String, PrefixExpression.Operator> unopMap;

  final static HashMap<String, PostfixExpression.Operator> incdecMap;

  static {
    binopMap = new HashMap<String, InfixExpression.Operator>(16);
    StaticJavaASTBuilder.binopMap.put(
            "||",
            InfixExpression.Operator.CONDITIONAL_OR);
    StaticJavaASTBuilder.binopMap.put(
            "&&",
            InfixExpression.Operator.CONDITIONAL_AND);
    StaticJavaASTBuilder.binopMap.put(
            "==",
            InfixExpression.Operator.EQUALS);
    StaticJavaASTBuilder.binopMap.put(
            "!=",
            InfixExpression.Operator.NOT_EQUALS);
    StaticJavaASTBuilder.binopMap.put(
            "==",
            InfixExpression.Operator.EQUALS);
    StaticJavaASTBuilder.binopMap.put(
            "<",
            InfixExpression.Operator.LESS);
    StaticJavaASTBuilder.binopMap.put(
            ">",
            InfixExpression.Operator.GREATER);
    StaticJavaASTBuilder.binopMap.put(
            "<=",
            InfixExpression.Operator.LESS_EQUALS);
    StaticJavaASTBuilder.binopMap.put(
            ">=",
            InfixExpression.Operator.GREATER_EQUALS);
    StaticJavaASTBuilder.binopMap.put(
            "+",
            InfixExpression.Operator.PLUS);
    StaticJavaASTBuilder.binopMap.put(
            "-",
            InfixExpression.Operator.MINUS);
    StaticJavaASTBuilder.binopMap.put(
            "*",
            InfixExpression.Operator.TIMES);
    StaticJavaASTBuilder.binopMap.put(
            "/",
            InfixExpression.Operator.DIVIDE);
    StaticJavaASTBuilder.binopMap.put(
            "%",
            InfixExpression.Operator.REMAINDER);
    StaticJavaASTBuilder.binopMap.put("|", InfixExpression.Operator.OR);
    StaticJavaASTBuilder.binopMap
            .put("^", InfixExpression.Operator.XOR);
    StaticJavaASTBuilder.binopMap
            .put("&", InfixExpression.Operator.AND);
    StaticJavaASTBuilder.binopMap.put(
            "<<",
            InfixExpression.Operator.LEFT_SHIFT);
    StaticJavaASTBuilder.binopMap.put(
            ">>",
            InfixExpression.Operator.RIGHT_SHIFT_SIGNED);
    StaticJavaASTBuilder.binopMap.put(
            ">>>",
            InfixExpression.Operator.RIGHT_SHIFT_UNSIGNED);

    unopMap = new HashMap<String, PrefixExpression.Operator>(4);
    StaticJavaASTBuilder.unopMap.put(
            "+",
            PrefixExpression.Operator.PLUS);
    StaticJavaASTBuilder.unopMap.put(
            "-",
            PrefixExpression.Operator.MINUS);
    StaticJavaASTBuilder.unopMap
            .put("!", PrefixExpression.Operator.NOT);
    StaticJavaASTBuilder.unopMap.put(
            "~",
            PrefixExpression.Operator.COMPLEMENT);

    incdecMap = new HashMap<String, PostfixExpression.Operator>(2);
    StaticJavaASTBuilder.incdecMap.put(
            "++",
            PostfixExpression.Operator.INCREMENT);
    StaticJavaASTBuilder.incdecMap.put(
            "--",
            PostfixExpression.Operator.DECREMENT);
  }

  protected AST ast = AST.newAST(AST.JLS8);

  private StaticJavaASTBuilder() {
  }

  @SuppressWarnings("unchecked")
  private void add(@SuppressWarnings("rawtypes") final List l, final Object o) {
    l.add(o);
  }

  @SuppressWarnings("unchecked")
  private <T extends ASTNode> T build(final ParserRuleContext tree) {
    return (T) visit(tree);
  }

  private <E extends ParserRuleContext> void builds(
          @SuppressWarnings("rawtypes") final List l, final List<E> trees) {
    if (trees != null) {
      for (final E e : trees) {
        add(l, build(e));
      }
    }
  }

  @Override
  public ArrayAccess visitArrayLHS(final ArrayLHSContext ctx) {
    final ArrayAccess result = this.ast.newArrayAccess();

    // TODO

    return result;
  }

  @Override
  public Expression visitAssign(final AssignContext ctx) {
    final Assignment result = this.ast.newAssignment();

    result.setLeftHandSide(this.build(ctx.leftHandSide()));

    result.setRightHandSide(this.build(ctx.exp()));

    return result;
  }

  @Override
  public ExpressionStatement visitAssignStatement(
          final AssignStatementContext ctx) {
    return this.ast
            .newExpressionStatement(this.build(ctx.assign()));
  }

  @Override
  public InfixExpression visitBinaryExp(final BinaryExpContext ctx) {
    final InfixExpression result = this.ast.newInfixExpression();

    result.setLeftOperand(this.build(ctx.e1));

    result.setOperator(StaticJavaASTBuilder.binopMap.get(ctx.op
            .getText()));

    result.setRightOperand(this.build(ctx.e2));

    return result;
  }

  @Override
  public PrimitiveType visitBooleanType(final BooleanTypeContext ctx) {
    // TODO
  }

  @Override
  public TypeDeclaration visitClassDefinition(final ClassDefinitionContext ctx) {
    final TypeDeclaration result = this.ast.newTypeDeclaration();

    // TODO

    return result;
  }

  @Override
  public CompilationUnit visitCompilationUnit(final CompilationUnitContext ctx) {
    final CompilationUnit result = this.ast.newCompilationUnit();

    final List<SimpleClassDefinitionContext> scds1 = ctx.scds1;
    if (scds1 != null) {
      builds(result.types(), scds1);
    }

    add(result.types(), this.<TypeDeclaration> build(ctx.classDefinition()));

    final List<SimpleClassDefinitionContext> scds2 = ctx.scds2;
    if (scds2 != null) {
      builds(result.types(), scds2);
    }

    return result;
  }

  @Override
  public ASTNode visitCondExp(final CondExpContext ctx) {
    final ConditionalExpression result = this.ast.newConditionalExpression();

    result.setExpression(this.<Expression> build(ctx.e1));

    result.setThenExpression(this.<Expression> build(ctx.e2));

    result.setElseExpression(this.<Expression> build(ctx.e3));

    return result;
  }

  @Override
  public DoStatement visitDoWhileStatement(final DoWhileStatementContext ctx) {
    final DoStatement result = this.ast.newDoStatement();

    // TODO

    return result;
  }

  @Override
  public BooleanLiteral visitFalseLiteral(final FalseLiteralContext ctx) {
    return this.ast.newBooleanLiteral(false);
  }

  @Override
  public FieldDeclaration visitFieldDeclaration(
          final FieldDeclarationContext ctx) {
    final VariableDeclarationFragment vdf = this.ast
            .newVariableDeclarationFragment();
    
    // TODO

    return result;
  }

  @Override
  public FieldAccess visitFieldLHS(final FieldLHSContext ctx) {
    final FieldAccess result = this.ast.newFieldAccess();

    // TODO

    return result;
  }

  @Override
  public ForStatement visitForStatement(final ForStatementContext ctx) {
    final ForStatement result = this.ast.newForStatement();
    
    // TODO

    return result;
  }

  @Override
  public SimpleName visitIdExp(final IdExpContext ctx) {
    return this.ast.newSimpleName(ctx.ID().getText());
  }

  @Override
  public SimpleName visitIdLHS(final IdLHSContext ctx) {
    // TODO
  }

  @Override
  public IfStatement visitIfStatement(final IfStatementContext ctx) {
    final IfStatement result = this.ast.newIfStatement();
    final Block thenBlock = this.ast.newBlock();
    result.setThenStatement(thenBlock);
    final Block elseBlock = this.ast.newBlock();
    result.setElseStatement(elseBlock);

    result.setExpression(this.build(ctx.exp()));

    final List<StatementContext> ts = ctx.ts;
    if (ts != null) {
      builds(thenBlock.statements(), ts);
    }

    final List<StatementContext> fs = ctx.fs;
    if (fs != null) {
      builds(elseBlock.statements(), fs);
    }

    return result;
  }

  @Override
  public PostfixExpression visitIncdec(final IncdecContext ctx) {
    final PostfixExpression result = this.ast.newPostfixExpression();

    // TODO

    return result;
  }

  @Override
  public ExpressionStatement visitIncdecStatement(
          final IncdecStatementContext ctx) {
    return this.ast
            .newExpressionStatement(this.<Expression> build(ctx.incdec()));
  }

  @Override
  public NumberLiteral visitIntLiteral(final IntLiteralContext ctx) {
    final NumberLiteral result = this.ast.newNumberLiteral();
    result.setToken(ctx.getText());
    return result;
  }

  @Override
  public PrimitiveType visitIntType(final IntTypeContext ctx) {
    // TODO
  }

  @Override
  public MethodInvocation visitInvoke(final InvokeContext ctx) {
    final MethodInvocation result = this.ast.newMethodInvocation();

    // TODO

    return result;
  }

  @Override
  public MethodInvocation visitInvokeExp(final InvokeExpContext ctx) {
    // TODO
  }

  @Override
  public ExpressionStatement visitInvokeExpStatement(
          final InvokeExpStatementContext ctx) {
    return this.ast.newExpressionStatement(this.<MethodInvocation> build(ctx
            .invoke()));
  }

  @Override
  public VariableDeclarationStatement visitLocalDeclaration(
          final LocalDeclarationContext ctx) {
    // TODO
  }

  @Override
  public MethodDeclaration visitMainMethodDeclaration(
          final MainMethodDeclarationContext ctx) {
    final MethodDeclaration result = this.ast.newMethodDeclaration();
    
    // TODO

    return result;
  }

  @Override
  public Block visitMethodBody(final MethodBodyContext ctx) {
    final Block result = this.ast.newBlock();

    // TODO

    return result;
  }

  @Override
  public MethodDeclaration visitMethodDeclaration(
          final MethodDeclarationContext ctx) {
    final MethodDeclaration result = this.ast.newMethodDeclaration();
    add(
            result.modifiers(),
            this.ast.newModifier(Modifier.ModifierKeyword.STATIC_KEYWORD));

    result.setReturnType2(this.build(ctx.returnType()));

    result.setName(this.ast.newSimpleName(ctx.ID().getText()));

    final ParamsContext params = ctx.params();
    if (params != null) {
      builds(result.parameters(), params.param());
    }

    result.setBody(this.<Block> build(ctx.methodBody()));

    return result;
  }

  @Override
  public ASTNode visitNewArrayBasic(final NewArrayBasicContext ctx) {
    final ArrayCreation result = this.ast.newArrayCreation();

    // TODO

    return result;
  }

  @Override
  public ASTNode visitNewArrayBasicInit(final NewArrayBasicInitContext ctx) {
    final ArrayCreation result = this.ast.newArrayCreation();
    
    // TODO

    return result;
  }

  @Override
  public ArrayCreation visitNewArrayObject(final NewArrayObjectContext ctx) {
    final ArrayCreation result = this.ast.newArrayCreation();

    // TODO

    return result;
  }

  @Override
  public ASTNode visitNewArrayObjectInit(final NewArrayObjectInitContext ctx) {
    final ArrayCreation result = this.ast.newArrayCreation();
    
    // TODO

    return result;
  }

  @Override
  public ASTNode visitNewObject(final NewObjectContext ctx) {
    final ClassInstanceCreation result = this.ast.newClassInstanceCreation();

    // TODO

    return result;
  }

  @Override
  public Type visitNonVoidReturnType(final NonVoidReturnTypeContext ctx) {
    return this.<Type> build(ctx.type());
  }

  @Override
  public NullLiteral visitNullLiteral(final NullLiteralContext ctx) {
    return this.ast.newNullLiteral();
  }

  @Override
  public SingleVariableDeclaration visitParam(final ParamContext ctx) {
    final SingleVariableDeclaration result = this.ast
            .newSingleVariableDeclaration();

    // TODO

    return result;
  }

  @Override
  public ParenthesizedExpression visitParenExp(final ParenExpContext ctx) {
    final ParenthesizedExpression result = this.ast
            .newParenthesizedExpression();

    // TODO

    return result;
  }

  @Override
  public Expression visitPrimaryExp(final PrimaryExpContext ctx) {
    return this.<Expression> build(ctx.pExp());
  }

  @Override
  public Expression visitPrimaryPrefix(final PrimaryPrefixContext ctx) {
    Expression result = this.<Expression> build(ctx.pPrefix());
    
    // TODO

    return result;
  }

  @Override
  public ASTNode visitPublicFieldDeclaration(
          final PublicFieldDeclarationContext ctx) {
    final VariableDeclarationFragment vdf = this.ast
            .newVariableDeclarationFragment();
    final FieldDeclaration result = this.ast.newFieldDeclaration(vdf);
    
    // TODO

    return result;
  }

  @Override
  public ReturnStatement visitReturnStatement(final ReturnStatementContext ctx) {
    final ReturnStatement result = this.ast.newReturnStatement();

    // TODO

    return result;
  }

  @Override
  public ASTNode visitShiftBinaryExp(final ShiftBinaryExpContext ctx) {
    final InfixExpression result = this.ast.newInfixExpression();

    // TODO

    return result;
  }

  @Override
  public ASTNode visitSimpleClassDefinition(
          final SimpleClassDefinitionContext ctx) {
    final TypeDeclaration result = this.ast.newTypeDeclaration();

    // TODO

    return result;
  }

  @Override
  public BooleanLiteral visitTrueLiteral(final TrueLiteralContext ctx) {
    return this.ast.newBooleanLiteral(true);
  }

  @Override
  public ASTNode visitType(final TypeContext ctx) {
    // TODO
  }

  @Override
  public PrefixExpression visitUnaryExp(final UnaryExpContext ctx) {
    final PrefixExpression result = this.ast.newPrefixExpression();

    // TODO

    return result;
  }

  @Override
  public PrimitiveType visitVoidType(final VoidTypeContext ctx) {
    return this.ast.newPrimitiveType(PrimitiveType.VOID);
  }

  @Override
  public WhileStatement visitWhileStatement(final WhileStatementContext ctx) {
    final WhileStatement result = this.ast.newWhileStatement();

    // TODO
    
    return result;
  }
}
