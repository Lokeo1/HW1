// Generated from sjc-parser/src/main/java/sjc/parser/StaticJava.g4 by ANTLR 4.13.1

package sjc.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link StaticJavaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface StaticJavaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(StaticJavaParser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#simpleClassDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleClassDefinition(StaticJavaParser.SimpleClassDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#publicFieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPublicFieldDeclaration(StaticJavaParser.PublicFieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#classDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDefinition(StaticJavaParser.ClassDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(StaticJavaParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#mainMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainMethodDeclaration(StaticJavaParser.MainMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(StaticJavaParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(StaticJavaParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(StaticJavaParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanType}
	 * labeled alternative in {@link StaticJavaParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanType(StaticJavaParser.BooleanTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link StaticJavaParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(StaticJavaParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VoidType}
	 * labeled alternative in {@link StaticJavaParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoidType(StaticJavaParser.VoidTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NonVoidReturnType}
	 * labeled alternative in {@link StaticJavaParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonVoidReturnType(StaticJavaParser.NonVoidReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(StaticJavaParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(StaticJavaParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#methodBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodBody(StaticJavaParser.MethodBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#localDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalDeclaration(StaticJavaParser.LocalDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(StaticJavaParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#assignStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStatement(StaticJavaParser.AssignStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(StaticJavaParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdLHS}
	 * labeled alternative in {@link StaticJavaParser#leftHandSide}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdLHS(StaticJavaParser.IdLHSContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FieldLHS}
	 * labeled alternative in {@link StaticJavaParser#leftHandSide}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldLHS(StaticJavaParser.FieldLHSContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayLHS}
	 * labeled alternative in {@link StaticJavaParser#leftHandSide}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLHS(StaticJavaParser.ArrayLHSContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(StaticJavaParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(StaticJavaParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(StaticJavaParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#assigns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssigns(StaticJavaParser.AssignsContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#incdecs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncdecs(StaticJavaParser.IncdecsContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#incdec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncdec(StaticJavaParser.IncdecContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#doWhileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStatement(StaticJavaParser.DoWhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#incdecStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncdecStatement(StaticJavaParser.IncdecStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#invokeExpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvokeExpStatement(StaticJavaParser.InvokeExpStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(StaticJavaParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ShiftBinaryExp}
	 * labeled alternative in {@link StaticJavaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftBinaryExp(StaticJavaParser.ShiftBinaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CondExp}
	 * labeled alternative in {@link StaticJavaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondExp(StaticJavaParser.CondExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryExp}
	 * labeled alternative in {@link StaticJavaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExp(StaticJavaParser.UnaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExp}
	 * labeled alternative in {@link StaticJavaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExp(StaticJavaParser.BinaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryExp}
	 * labeled alternative in {@link StaticJavaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExp(StaticJavaParser.PrimaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LeftShiftOp}
	 * labeled alternative in {@link StaticJavaParser#shiftOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftShiftOp(StaticJavaParser.LeftShiftOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RightShiftOp}
	 * labeled alternative in {@link StaticJavaParser#shiftOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightShiftOp(StaticJavaParser.RightShiftOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code URightShiftOp}
	 * labeled alternative in {@link StaticJavaParser#shiftOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitURightShiftOp(StaticJavaParser.URightShiftOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link StaticJavaParser#pExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(StaticJavaParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TrueLiteral}
	 * labeled alternative in {@link StaticJavaParser#pExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueLiteral(StaticJavaParser.TrueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FalseLiteral}
	 * labeled alternative in {@link StaticJavaParser#pExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseLiteral(StaticJavaParser.FalseLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NullLiteral}
	 * labeled alternative in {@link StaticJavaParser#pExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(StaticJavaParser.NullLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryPrefix}
	 * labeled alternative in {@link StaticJavaParser#pExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryPrefix(StaticJavaParser.PrimaryPrefixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AccessSuffix}
	 * labeled alternative in {@link StaticJavaParser#pSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessSuffix(StaticJavaParser.AccessSuffixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IndexingSuffix}
	 * labeled alternative in {@link StaticJavaParser#pSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexingSuffix(StaticJavaParser.IndexingSuffixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExp}
	 * labeled alternative in {@link StaticJavaParser#pPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExp(StaticJavaParser.ParenExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InvokeExp}
	 * labeled alternative in {@link StaticJavaParser#pPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvokeExp(StaticJavaParser.InvokeExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdExp}
	 * labeled alternative in {@link StaticJavaParser#pPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExp(StaticJavaParser.IdExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewArrayObject}
	 * labeled alternative in {@link StaticJavaParser#pPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayObject(StaticJavaParser.NewArrayObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewArrayObjectInit}
	 * labeled alternative in {@link StaticJavaParser#pPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayObjectInit(StaticJavaParser.NewArrayObjectInitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewObject}
	 * labeled alternative in {@link StaticJavaParser#pPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewObject(StaticJavaParser.NewObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewArrayBasic}
	 * labeled alternative in {@link StaticJavaParser#pPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayBasic(StaticJavaParser.NewArrayBasicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewArrayBasicInit}
	 * labeled alternative in {@link StaticJavaParser#pPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayBasicInit(StaticJavaParser.NewArrayBasicInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#invoke}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvoke(StaticJavaParser.InvokeContext ctx);
	/**
	 * Visit a parse tree produced by {@link StaticJavaParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(StaticJavaParser.ArgsContext ctx);
}