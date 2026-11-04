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

    result.setArray(this.<Expression> build(ctx.e));

    result.setIndex(this.<Expression> build(ctx.idx));

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
    return this.ast.newPrimitiveType(PrimitiveType.BOOLEAN);
  }

  @Override
  public TypeDeclaration visitClassDefinition(final ClassDefinitionContext ctx) {
    final TypeDeclaration result = this.ast.newTypeDeclaration();
    add(
            result.modifiers(),
            this.ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));

    result.setName(this.ast.newSimpleName(ctx.ID().getText()));

    add(
            result.bodyDeclarations(),
            this.<MethodDeclaration> build(ctx.mainMethodDeclaration()));

    final List<MemberDeclarationContext> memberDeclarations = ctx
            .memberDeclaration();
    if (memberDeclarations != null) {
      builds(result.bodyDeclarations(), memberDeclarations);
    }

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
    final Block whileBody = this.ast.newBlock();
    result.setBody(whileBody);

    final List<StatementContext> statements = ctx.statement();
    if (statements != null) {
      builds(whileBody.statements(), statements);
    }

    result.setExpression(this.<Expression> build(ctx.exp()));

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
    final FieldDeclaration result = this.ast.newFieldDeclaration(vdf);
    add(
            result.modifiers(),
            this.ast.newModifier(Modifier.ModifierKeyword.STATIC_KEYWORD));

    result.setType(this.<Type> build(ctx.type()));

    vdf.setName(this.ast.newSimpleName(ctx.ID().getText()));

    return result;
  }

  @Override
  public FieldAccess visitFieldLHS(final FieldLHSContext ctx) {
    final FieldAccess result = this.ast.newFieldAccess();

    result.setExpression(this.<Expression> build(ctx.exp()));

    result.setName(this.ast.newSimpleName(ctx.ID().getText()));

    return result;
  }

  @Override
  public ForStatement visitForStatement(final ForStatementContext ctx) {
    final ForStatement result = this.ast.newForStatement();
    final Block forStatement = this.ast.newBlock();
    result.setBody(forStatement);

    final AssignsContext assigns = ctx.assigns();
    if (assigns != null) {
      builds(result.initializers(), assigns.assign());
    }

    final ExpContext e = ctx.exp();
    if (e != null) {
      result.setExpression(this.<Expression> build(e));
    }

    final IncdecsContext incdecs = ctx.incdecs();
    if (incdecs != null) {
      builds(result.updaters(), incdecs.incdec());
    }

    final List<StatementContext> statements = ctx.statement();
    if (statements != null) {
      builds(forStatement.statements(), statements);
    }

    return result;
  }

  @Override
  public SimpleName visitIdExp(final IdExpContext ctx) {
    return this.ast.newSimpleName(ctx.ID().getText());
  }

  @Override
  public SimpleName visitIdLHS(final IdLHSContext ctx) {
    return this.ast.newSimpleName(ctx.ID().getText());
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

    result.setOperand(this.<Expression> build(ctx.leftHandSide()));

    result.setOperator(StaticJavaASTBuilder.incdecMap.get(ctx.op
            .getText()));

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
    return this.ast.newPrimitiveType(PrimitiveType.INT);
  }

  @Override
  public MethodInvocation visitInvoke(final InvokeContext ctx) {
    final MethodInvocation result = this.ast.newMethodInvocation();

    if (ctx.id1 != null) {
      result.setExpression(this.ast.newSimpleName(ctx.id1.getText()));
    }

    result.setName(this.ast.newSimpleName(ctx.id2.getText()));

    final ArgsContext args = ctx.args();
    if (args != null) {
      builds(result.arguments(), args.exp());
    }

    return result;
  }

  @Override
  public MethodInvocation visitInvokeExp(final InvokeExpContext ctx) {
    return this.<MethodInvocation> build(ctx.invoke());
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
    final VariableDeclarationFragment vdf = this.ast
            .newVariableDeclarationFragment();
    final VariableDeclarationStatement result = this.ast
            .newVariableDeclarationStatement(vdf);

    result.setType(this.<Type> build(ctx.type()));

    vdf.setName(this.ast.newSimpleName(ctx.ID().getText()));

    return result;
  }

  @Override
  public MethodDeclaration visitMainMethodDeclaration(
          final MainMethodDeclarationContext ctx) {
    final MethodDeclaration result = this.ast.newMethodDeclaration();
    add(
            result.modifiers(),
            this.ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
    add(
            result.modifiers(),
            this.ast.newModifier(Modifier.ModifierKeyword.STATIC_KEYWORD));
    result.setReturnType2(this.ast.newPrimitiveType(PrimitiveType.VOID));
    result.setName(this.ast.newSimpleName("main"));

    final SingleVariableDeclaration svd = this.ast
            .newSingleVariableDeclaration();
    svd.setType(this.ast.newArrayType(this.ast.newSimpleType(this.ast
            .newSimpleName("String"))));
    svd.setName(this.ast.newSimpleName(ctx.id3.getText()));
    add(result.parameters(), svd);

    result.setBody(this.<Block> build(ctx.methodBody()));

    return result;
  }

  @Override
  public Block visitMethodBody(final MethodBodyContext ctx) {
    final Block result = this.ast.newBlock();

    final List<LocalDeclarationContext> localDeclarations = ctx
            .localDeclaration();
    if (localDeclarations != null) {
      builds(result.statements(), localDeclarations);
    }

    final List<StatementContext> statements = ctx.statement();
    if (statements != null) {
      builds(result.statements(), statements);
    }

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

    result.setType(this.ast.newArrayType(this.<Type> build(ctx.basicType())));

    add(result.dimensions(), this.<Expression> build(ctx.exp()));

    return result;
  }

  @Override
  public ASTNode visitNewArrayBasicInit(final NewArrayBasicInitContext ctx) {
    final ArrayCreation result = this.ast.newArrayCreation();
    final ArrayInitializer init = this.ast.newArrayInitializer();
    result.setInitializer(init);

    result.setType(this.ast.newArrayType(this.<Type> build(ctx.basicType())));

    final ArgsContext args = ctx.args();
    if (args != null) {
      builds(init.expressions(), args.exp());
    }

    return result;
  }

  @Override
  public ArrayCreation visitNewArrayObject(final NewArrayObjectContext ctx) {
    final ArrayCreation result = this.ast.newArrayCreation();

    result.setType(this.ast.newArrayType(this.ast.newSimpleType(this.ast
            .newSimpleName(ctx.ID().getText()))));

    add(result.dimensions(), this.<Expression> build(ctx.exp()));

    return result;
  }

  @Override
  public ASTNode visitNewArrayObjectInit(final NewArrayObjectInitContext ctx) {
    final ArrayCreation result = this.ast.newArrayCreation();
    final ArrayInitializer init = this.ast.newArrayInitializer();
    result.setInitializer(init);

    result.setType(this.ast.newArrayType(this.ast.newSimpleType(this.ast
            .newSimpleName(ctx.ID().getText()))));

    final ArgsContext args = ctx.args();
    if (args != null) {
      builds(init.expressions(), args.exp());
    }

    return result;
  }

  @Override
  public ASTNode visitNewObject(final NewObjectContext ctx) {
    final ClassInstanceCreation result = this.ast.newClassInstanceCreation();

    result.setType(this.ast.newSimpleType(this.ast.newSimpleName(ctx.ID()
            .getText())));

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

    result.setType(this.<Type> build(ctx.type()));

    result.setName(this.ast.newSimpleName(ctx.ID().getText()));

    return result;
  }

  @Override
  public ParenthesizedExpression visitParenExp(final ParenExpContext ctx) {
    final ParenthesizedExpression result = this.ast
            .newParenthesizedExpression();

    result.setExpression(this.<Expression> build(ctx.exp()));

    return result;
  }

  @Override
  public Expression visitPrimaryExp(final PrimaryExpContext ctx) {
    return this.<Expression> build(ctx.pExp());
  }

  @Override
  public Expression visitPrimaryPrefix(final PrimaryPrefixContext ctx) {
    Expression result = this.<Expression> build(ctx.pPrefix());
    final List<PSuffixContext> pSuffix = ctx.pSuffix();
    if (pSuffix != null) {
      for (final PSuffixContext psc : pSuffix) {
        if (psc instanceof AccessSuffixContext) {
          final AccessSuffixContext asc = (AccessSuffixContext) psc;

          final FieldAccess fa = this.ast.newFieldAccess();

          fa.setExpression(result);

          fa.setName(this.ast.newSimpleName(asc.ID().getText()));

          result = fa;

        } else {
          final IndexingSuffixContext isc = (IndexingSuffixContext) psc;

          final ArrayAccess aa = this.ast.newArrayAccess();

          aa.setArray(result);

          aa.setIndex(this.<Expression> build(isc.exp()));

          result = aa;
        }
      }
    }

    return result;
  }

  @Override
  public ASTNode visitPublicFieldDeclaration(
          final PublicFieldDeclarationContext ctx) {
    final VariableDeclarationFragment vdf = this.ast
            .newVariableDeclarationFragment();
    final FieldDeclaration result = this.ast.newFieldDeclaration(vdf);
    add(
            result.modifiers(),
            this.ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));

    result.setType(this.<Type> build(ctx.type()));

    vdf.setName(this.ast.newSimpleName(ctx.ID().getText()));

    return result;
  }

  @Override
  public ReturnStatement visitReturnStatement(final ReturnStatementContext ctx) {
    final ReturnStatement result = this.ast.newReturnStatement();

    final ExpContext exp = ctx.exp();
    if (exp != null) {
      result.setExpression(this.<Expression> build(exp));
    }

    return result;
  }

  @Override
  public ASTNode visitShiftBinaryExp(final ShiftBinaryExpContext ctx) {
    final InfixExpression result = this.ast.newInfixExpression();

    result.setLeftOperand(this.<Expression> build(ctx.e1));

    final ShiftOpContext sop = ctx.shiftOp();
    final String op = sop instanceof LeftShiftOpContext ? "<<"
            : (sop instanceof RightShiftOpContext ? ">>" : ">>>");
    result.setOperator(StaticJavaASTBuilder.binopMap.get(op));

    result.setRightOperand(this.<Expression> build(ctx.e2));

    return result;
  }

  @Override
  public ASTNode visitSimpleClassDefinition(
          final SimpleClassDefinitionContext ctx) {
    final TypeDeclaration result = this.ast.newTypeDeclaration();

    result.setName(this.ast.newSimpleName(ctx.ID().getText()));

    final List<PublicFieldDeclarationContext> fieldDeclarations = ctx
            .publicFieldDeclaration();
    if (fieldDeclarations != null) {
      builds(result.bodyDeclarations(), fieldDeclarations);
    }

    return result;
  }

  @Override
  public BooleanLiteral visitTrueLiteral(final TrueLiteralContext ctx) {
    return this.ast.newBooleanLiteral(true);
  }

  @Override
  public ASTNode visitType(final TypeContext ctx) {
    Type result = ctx.basicType() == null ? this.ast.newSimpleType(this.ast
            .newSimpleName(ctx.ID().getText())) : this
            .<Type> build(ctx.basicType());
    if (ctx.t != null) {
      result = this.ast.newArrayType(result);
    }
    return result;
  }

  @Override
  public PrefixExpression visitUnaryExp(final UnaryExpContext ctx) {
    final PrefixExpression result = this.ast.newPrefixExpression();

    result.setOperator(StaticJavaASTBuilder.unopMap.get(ctx.op
            .getText()));

    result.setOperand(this.<Expression> build(ctx.exp()));

    return result;
  }

  @Override
  public PrimitiveType visitVoidType(final VoidTypeContext ctx) {
    return this.ast.newPrimitiveType(PrimitiveType.VOID);
  }

  @Override
  public WhileStatement visitWhileStatement(final WhileStatementContext ctx) {
    final WhileStatement result = this.ast.newWhileStatement();
    final Block whileBody = this.ast.newBlock();
    result.setBody(whileBody);

    result.setExpression(this.<Expression> build(ctx.exp()));

    final List<StatementContext> statements = ctx.statement();
    if (statements != null) {
      builds(whileBody.statements(), statements);
    }

    return result;
  }
}
