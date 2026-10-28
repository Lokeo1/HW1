// Generated from sjc-parser/src/main/java/sjc/parser/StaticJava.g4 by ANTLR 4.13.1

package sjc.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class StaticJavaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, ID=49, INT=50, WS=51, ERROR=52;
	public static final int
		RULE_compilationUnit = 0, RULE_simpleClassDefinition = 1, RULE_publicFieldDeclaration = 2, 
		RULE_classDefinition = 3, RULE_memberDeclaration = 4, RULE_mainMethodDeclaration = 5, 
		RULE_fieldDeclaration = 6, RULE_methodDeclaration = 7, RULE_type = 8, 
		RULE_basicType = 9, RULE_returnType = 10, RULE_params = 11, RULE_param = 12, 
		RULE_methodBody = 13, RULE_localDeclaration = 14, RULE_statement = 15, 
		RULE_assignStatement = 16, RULE_assign = 17, RULE_leftHandSide = 18, RULE_ifStatement = 19, 
		RULE_whileStatement = 20, RULE_forStatement = 21, RULE_assigns = 22, RULE_incdecs = 23, 
		RULE_incdec = 24, RULE_doWhileStatement = 25, RULE_incdecStatement = 26, 
		RULE_invokeExpStatement = 27, RULE_returnStatement = 28, RULE_exp = 29, 
		RULE_shiftOp = 30, RULE_pExp = 31, RULE_pSuffix = 32, RULE_pPrefix = 33, 
		RULE_invoke = 34, RULE_args = 35;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "simpleClassDefinition", "publicFieldDeclaration", 
			"classDefinition", "memberDeclaration", "mainMethodDeclaration", "fieldDeclaration", 
			"methodDeclaration", "type", "basicType", "returnType", "params", "param", 
			"methodBody", "localDeclaration", "statement", "assignStatement", "assign", 
			"leftHandSide", "ifStatement", "whileStatement", "forStatement", "assigns", 
			"incdecs", "incdec", "doWhileStatement", "incdecStatement", "invokeExpStatement", 
			"returnStatement", "exp", "shiftOp", "pExp", "pSuffix", "pPrefix", "invoke", 
			"args"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "'public'", "';'", "'static'", "'void'", 
			"'('", "'['", "']'", "')'", "'boolean'", "'int'", "','", "'='", "'.'", 
			"'if'", "'else'", "'while'", "'for'", "'++'", "'--'", "'do'", "'return'", 
			"'-'", "'+'", "'!'", "'~'", "'*'", "'/'", "'%'", "'<'", "'>'", "'<='", 
			"'>='", "'=='", "'!='", "'&'", "'^'", "'|'", "'&&'", "'||'", "'?'", "':'", 
			"'true'", "'false'", "'null'", "'new'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "ID", "INT", "WS", "ERROR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "StaticJava.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public StaticJavaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompilationUnitContext extends ParserRuleContext {
		public SimpleClassDefinitionContext simpleClassDefinition;
		public List<SimpleClassDefinitionContext> scds1 = new ArrayList<SimpleClassDefinitionContext>();
		public List<SimpleClassDefinitionContext> scds2 = new ArrayList<SimpleClassDefinitionContext>();
		public ClassDefinitionContext classDefinition() {
			return getRuleContext(ClassDefinitionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(StaticJavaParser.EOF, 0); }
		public List<SimpleClassDefinitionContext> simpleClassDefinition() {
			return getRuleContexts(SimpleClassDefinitionContext.class);
		}
		public SimpleClassDefinitionContext simpleClassDefinition(int i) {
			return getRuleContext(SimpleClassDefinitionContext.class,i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(72);
				((CompilationUnitContext)_localctx).simpleClassDefinition = simpleClassDefinition();
				((CompilationUnitContext)_localctx).scds1.add(((CompilationUnitContext)_localctx).simpleClassDefinition);
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
			classDefinition();
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(79);
				((CompilationUnitContext)_localctx).simpleClassDefinition = simpleClassDefinition();
				((CompilationUnitContext)_localctx).scds2.add(((CompilationUnitContext)_localctx).simpleClassDefinition);
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(85);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleClassDefinitionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public List<PublicFieldDeclarationContext> publicFieldDeclaration() {
			return getRuleContexts(PublicFieldDeclarationContext.class);
		}
		public PublicFieldDeclarationContext publicFieldDeclaration(int i) {
			return getRuleContext(PublicFieldDeclarationContext.class,i);
		}
		public SimpleClassDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleClassDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitSimpleClassDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleClassDefinitionContext simpleClassDefinition() throws RecognitionException {
		SimpleClassDefinitionContext _localctx = new SimpleClassDefinitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_simpleClassDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(T__0);
			setState(88);
			match(ID);
			setState(89);
			match(T__1);
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(90);
				publicFieldDeclaration();
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(96);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PublicFieldDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public PublicFieldDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_publicFieldDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitPublicFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PublicFieldDeclarationContext publicFieldDeclaration() throws RecognitionException {
		PublicFieldDeclarationContext _localctx = new PublicFieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_publicFieldDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(T__3);
			setState(99);
			type();
			setState(100);
			match(ID);
			setState(101);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassDefinitionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public MainMethodDeclarationContext mainMethodDeclaration() {
			return getRuleContext(MainMethodDeclarationContext.class,0);
		}
		public List<MemberDeclarationContext> memberDeclaration() {
			return getRuleContexts(MemberDeclarationContext.class);
		}
		public MemberDeclarationContext memberDeclaration(int i) {
			return getRuleContext(MemberDeclarationContext.class,i);
		}
		public ClassDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitClassDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefinitionContext classDefinition() throws RecognitionException {
		ClassDefinitionContext _localctx = new ClassDefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(T__3);
			setState(104);
			match(T__0);
			setState(105);
			match(ID);
			setState(106);
			match(T__1);
			setState(107);
			mainMethodDeclaration();
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(108);
				memberDeclaration();
				}
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(114);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MemberDeclarationContext extends ParserRuleContext {
		public FieldDeclarationContext fieldDeclaration() {
			return getRuleContext(FieldDeclarationContext.class,0);
		}
		public MethodDeclarationContext methodDeclaration() {
			return getRuleContext(MethodDeclarationContext.class,0);
		}
		public MemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberDeclarationContext memberDeclaration() throws RecognitionException {
		MemberDeclarationContext _localctx = new MemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_memberDeclaration);
		try {
			setState(118);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(116);
				fieldDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(117);
				methodDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MainMethodDeclarationContext extends ParserRuleContext {
		public Token id1;
		public Token id2;
		public Token id3;
		public MethodBodyContext methodBody() {
			return getRuleContext(MethodBodyContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(StaticJavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(StaticJavaParser.ID, i);
		}
		public MainMethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainMethodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitMainMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainMethodDeclarationContext mainMethodDeclaration() throws RecognitionException {
		MainMethodDeclarationContext _localctx = new MainMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_mainMethodDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			match(T__3);
			setState(121);
			match(T__5);
			setState(122);
			match(T__6);
			setState(123);
			((MainMethodDeclarationContext)_localctx).id1 = match(ID);
			setState(124);
			if (!( "main".equals((((MainMethodDeclarationContext)_localctx).id1!=null?((MainMethodDeclarationContext)_localctx).id1.getText():null)) )) throw new FailedPredicateException(this, " \"main\".equals($id1.text) ");
			setState(125);
			match(T__7);
			setState(126);
			((MainMethodDeclarationContext)_localctx).id2 = match(ID);
			setState(127);
			if (!( "String".equals((((MainMethodDeclarationContext)_localctx).id2!=null?((MainMethodDeclarationContext)_localctx).id2.getText():null)) )) throw new FailedPredicateException(this, " \"String\".equals($id2.text) ");
			setState(128);
			match(T__8);
			setState(129);
			match(T__9);
			setState(130);
			((MainMethodDeclarationContext)_localctx).id3 = match(ID);
			setState(131);
			match(T__10);
			setState(132);
			match(T__1);
			setState(133);
			methodBody();
			setState(134);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FieldDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public FieldDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDeclarationContext fieldDeclaration() throws RecognitionException {
		FieldDeclarationContext _localctx = new FieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_fieldDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			match(T__5);
			setState(137);
			type();
			setState(138);
			match(ID);
			setState(139);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodDeclarationContext extends ParserRuleContext {
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public MethodBodyContext methodBody() {
			return getRuleContext(MethodBodyContext.class,0);
		}
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_methodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(T__5);
			setState(142);
			returnType();
			setState(143);
			match(ID);
			setState(144);
			match(T__7);
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 562949953433600L) != 0)) {
				{
				setState(145);
				params();
				}
			}

			setState(148);
			match(T__10);
			setState(149);
			match(T__1);
			setState(150);
			methodBody();
			setState(151);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public Token t;
		public BasicTypeContext basicType() {
			return getRuleContext(BasicTypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
			case T__12:
				{
				setState(153);
				basicType();
				}
				break;
			case ID:
				{
				setState(154);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(157);
				((TypeContext)_localctx).t = match(T__8);
				setState(158);
				match(T__9);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BasicTypeContext extends ParserRuleContext {
		public BasicTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicType; }
	 
		public BasicTypeContext() { }
		public void copyFrom(BasicTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BooleanTypeContext extends BasicTypeContext {
		public BooleanTypeContext(BasicTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitBooleanType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntTypeContext extends BasicTypeContext {
		public IntTypeContext(BasicTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitIntType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BasicTypeContext basicType() throws RecognitionException {
		BasicTypeContext _localctx = new BasicTypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_basicType);
		try {
			setState(163);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(161);
				match(T__11);
				}
				break;
			case T__12:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(162);
				match(T__12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnTypeContext extends ParserRuleContext {
		public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnType; }
	 
		public ReturnTypeContext() { }
		public void copyFrom(ReturnTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VoidTypeContext extends ReturnTypeContext {
		public VoidTypeContext(ReturnTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitVoidType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NonVoidReturnTypeContext extends ReturnTypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public NonVoidReturnTypeContext(ReturnTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitNonVoidReturnType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnTypeContext returnType() throws RecognitionException {
		ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_returnType);
		try {
			setState(167);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				_localctx = new VoidTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(165);
				match(T__6);
				}
				break;
			case T__11:
			case T__12:
			case ID:
				_localctx = new NonVoidReturnTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(166);
				type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamsContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			param();
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13) {
				{
				{
				setState(170);
				match(T__13);
				setState(171);
				param();
				}
				}
				setState(176);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			type();
			setState(178);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodBodyContext extends ParserRuleContext {
		public List<LocalDeclarationContext> localDeclaration() {
			return getRuleContexts(LocalDeclarationContext.class);
		}
		public LocalDeclarationContext localDeclaration(int i) {
			return getRuleContext(LocalDeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MethodBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitMethodBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodBodyContext methodBody() throws RecognitionException {
		MethodBodyContext _localctx = new MethodBodyContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_methodBody);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(180);
					localDeclaration();
					}
					} 
				}
				setState(185);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615971782912L) != 0)) {
				{
				{
				setState(186);
				statement();
				}
				}
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LocalDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public LocalDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitLocalDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocalDeclarationContext localDeclaration() throws RecognitionException {
		LocalDeclarationContext _localctx = new LocalDeclarationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_localDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			type();
			setState(193);
			match(ID);
			setState(194);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public AssignStatementContext assignStatement() {
			return getRuleContext(AssignStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public DoWhileStatementContext doWhileStatement() {
			return getRuleContext(DoWhileStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public IncdecStatementContext incdecStatement() {
			return getRuleContext(IncdecStatementContext.class,0);
		}
		public InvokeExpStatementContext invokeExpStatement() {
			return getRuleContext(InvokeExpStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_statement);
		try {
			setState(204);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(196);
				assignStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(197);
				ifStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(198);
				whileStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(199);
				doWhileStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(200);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(201);
				incdecStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(202);
				invokeExpStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(203);
				returnStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignStatementContext extends ParserRuleContext {
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public AssignStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitAssignStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignStatementContext assignStatement() throws RecognitionException {
		AssignStatementContext _localctx = new AssignStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_assignStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			assign();
			setState(207);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignContext extends ParserRuleContext {
		public LeftHandSideContext leftHandSide() {
			return getRuleContext(LeftHandSideContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			leftHandSide();
			setState(210);
			match(T__14);
			setState(211);
			exp(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LeftHandSideContext extends ParserRuleContext {
		public LeftHandSideContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_leftHandSide; }
	 
		public LeftHandSideContext() { }
		public void copyFrom(LeftHandSideContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayLHSContext extends LeftHandSideContext {
		public ExpContext e;
		public ExpContext idx;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ArrayLHSContext(LeftHandSideContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitArrayLHS(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FieldLHSContext extends LeftHandSideContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public FieldLHSContext(LeftHandSideContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitFieldLHS(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdLHSContext extends LeftHandSideContext {
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public IdLHSContext(LeftHandSideContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitIdLHS(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LeftHandSideContext leftHandSide() throws RecognitionException {
		LeftHandSideContext _localctx = new LeftHandSideContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_leftHandSide);
		try {
			setState(223);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new IdLHSContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(213);
				match(ID);
				}
				break;
			case 2:
				_localctx = new FieldLHSContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(214);
				exp(0);
				setState(215);
				match(T__15);
				setState(216);
				match(ID);
				}
				break;
			case 3:
				_localctx = new ArrayLHSContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(218);
				((ArrayLHSContext)_localctx).e = exp(0);
				setState(219);
				match(T__8);
				setState(220);
				((ArrayLHSContext)_localctx).idx = exp(0);
				setState(221);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public StatementContext statement;
		public List<StatementContext> ts = new ArrayList<StatementContext>();
		public List<StatementContext> fs = new ArrayList<StatementContext>();
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			match(T__16);
			setState(226);
			match(T__7);
			setState(227);
			exp(0);
			setState(228);
			match(T__10);
			setState(229);
			match(T__1);
			setState(233);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615971782912L) != 0)) {
				{
				{
				setState(230);
				((IfStatementContext)_localctx).statement = statement();
				((IfStatementContext)_localctx).ts.add(((IfStatementContext)_localctx).statement);
				}
				}
				setState(235);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(236);
			match(T__2);
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(237);
				match(T__17);
				setState(238);
				match(T__1);
				setState(242);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615971782912L) != 0)) {
					{
					{
					setState(239);
					((IfStatementContext)_localctx).statement = statement();
					((IfStatementContext)_localctx).fs.add(((IfStatementContext)_localctx).statement);
					}
					}
					setState(244);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(245);
				match(T__2);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_whileStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			match(T__18);
			setState(249);
			match(T__7);
			setState(250);
			exp(0);
			setState(251);
			match(T__10);
			setState(252);
			match(T__1);
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615971782912L) != 0)) {
				{
				{
				setState(253);
				statement();
				}
				}
				setState(258);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(259);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForStatementContext extends ParserRuleContext {
		public AssignsContext assigns() {
			return getRuleContext(AssignsContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public IncdecsContext incdecs() {
			return getRuleContext(IncdecsContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			match(T__19);
			setState(262);
			match(T__7);
			setState(264);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615944913152L) != 0)) {
				{
				setState(263);
				assigns();
				}
			}

			setState(266);
			match(T__4);
			setState(268);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615944913152L) != 0)) {
				{
				setState(267);
				exp(0);
				}
			}

			setState(270);
			match(T__4);
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615944913152L) != 0)) {
				{
				setState(271);
				incdecs();
				}
			}

			setState(274);
			match(T__10);
			setState(275);
			match(T__1);
			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615971782912L) != 0)) {
				{
				{
				setState(276);
				statement();
				}
				}
				setState(281);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(282);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignsContext extends ParserRuleContext {
		public List<AssignContext> assign() {
			return getRuleContexts(AssignContext.class);
		}
		public AssignContext assign(int i) {
			return getRuleContext(AssignContext.class,i);
		}
		public AssignsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assigns; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitAssigns(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignsContext assigns() throws RecognitionException {
		AssignsContext _localctx = new AssignsContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_assigns);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			assign();
			setState(289);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13) {
				{
				{
				setState(285);
				match(T__13);
				setState(286);
				assign();
				}
				}
				setState(291);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IncdecsContext extends ParserRuleContext {
		public List<IncdecContext> incdec() {
			return getRuleContexts(IncdecContext.class);
		}
		public IncdecContext incdec(int i) {
			return getRuleContext(IncdecContext.class,i);
		}
		public IncdecsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_incdecs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitIncdecs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncdecsContext incdecs() throws RecognitionException {
		IncdecsContext _localctx = new IncdecsContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_incdecs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			incdec();
			setState(297);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13) {
				{
				{
				setState(293);
				match(T__13);
				setState(294);
				incdec();
				}
				}
				setState(299);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IncdecContext extends ParserRuleContext {
		public Token op;
		public LeftHandSideContext leftHandSide() {
			return getRuleContext(LeftHandSideContext.class,0);
		}
		public IncdecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_incdec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitIncdec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncdecContext incdec() throws RecognitionException {
		IncdecContext _localctx = new IncdecContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_incdec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			leftHandSide();
			setState(301);
			((IncdecContext)_localctx).op = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__20 || _la==T__21) ) {
				((IncdecContext)_localctx).op = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DoWhileStatementContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public DoWhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitDoWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_doWhileStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			match(T__22);
			setState(304);
			match(T__1);
			setState(308);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615971782912L) != 0)) {
				{
				{
				setState(305);
				statement();
				}
				}
				setState(310);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(311);
			match(T__2);
			setState(312);
			match(T__18);
			setState(313);
			match(T__7);
			setState(314);
			exp(0);
			setState(315);
			match(T__10);
			setState(316);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IncdecStatementContext extends ParserRuleContext {
		public IncdecContext incdec() {
			return getRuleContext(IncdecContext.class,0);
		}
		public IncdecStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_incdecStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitIncdecStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncdecStatementContext incdecStatement() throws RecognitionException {
		IncdecStatementContext _localctx = new IncdecStatementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_incdecStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			incdec();
			setState(319);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InvokeExpStatementContext extends ParserRuleContext {
		public InvokeContext invoke() {
			return getRuleContext(InvokeContext.class,0);
		}
		public InvokeExpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_invokeExpStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitInvokeExpStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InvokeExpStatementContext invokeExpStatement() throws RecognitionException {
		InvokeExpStatementContext _localctx = new InvokeExpStatementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_invokeExpStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(321);
			invoke();
			setState(322);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(T__23);
			setState(326);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615944913152L) != 0)) {
				{
				setState(325);
				exp(0);
				}
			}

			setState(328);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ShiftBinaryExpContext extends ExpContext {
		public ExpContext e1;
		public ExpContext e2;
		public ShiftOpContext shiftOp() {
			return getRuleContext(ShiftOpContext.class,0);
		}
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ShiftBinaryExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitShiftBinaryExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondExpContext extends ExpContext {
		public ExpContext e1;
		public ExpContext e2;
		public ExpContext e3;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public CondExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitCondExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExpContext extends ExpContext {
		public Token op;
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public UnaryExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitUnaryExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinaryExpContext extends ExpContext {
		public ExpContext e1;
		public Token op;
		public ExpContext e2;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public BinaryExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitBinaryExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryExpContext extends ExpContext {
		public PExpContext pExp() {
			return getRuleContext(PExpContext.class,0);
		}
		public PrimaryExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitPrimaryExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 58;
		enterRecursionRule(_localctx, 58, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
			case T__44:
			case T__45:
			case T__46:
			case T__47:
			case ID:
			case INT:
				{
				_localctx = new PrimaryExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(331);
				pExp();
				}
				break;
			case T__24:
			case T__25:
				{
				_localctx = new UnaryExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(332);
				((UnaryExpContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__24 || _la==T__25) ) {
					((UnaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(333);
				exp(13);
				}
				break;
			case T__26:
			case T__27:
				{
				_localctx = new UnaryExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(334);
				((UnaryExpContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__26 || _la==T__27) ) {
					((UnaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(335);
				exp(12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(377);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(375);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExpContext(new ExpContext(_parentctx, _parentState));
						((BinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(338);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(339);
						((BinaryExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 3758096384L) != 0)) ) {
							((BinaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(340);
						((BinaryExpContext)_localctx).e2 = exp(12);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExpContext(new ExpContext(_parentctx, _parentState));
						((BinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(341);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(342);
						((BinaryExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__24 || _la==T__25) ) {
							((BinaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(343);
						((BinaryExpContext)_localctx).e2 = exp(11);
						}
						break;
					case 3:
						{
						_localctx = new ShiftBinaryExpContext(new ExpContext(_parentctx, _parentState));
						((ShiftBinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(344);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(345);
						shiftOp();
						setState(346);
						((ShiftBinaryExpContext)_localctx).e2 = exp(10);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExpContext(new ExpContext(_parentctx, _parentState));
						((BinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(348);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(349);
						((BinaryExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 64424509440L) != 0)) ) {
							((BinaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(350);
						((BinaryExpContext)_localctx).e2 = exp(9);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExpContext(new ExpContext(_parentctx, _parentState));
						((BinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(351);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(352);
						((BinaryExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__35 || _la==T__36) ) {
							((BinaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(353);
						((BinaryExpContext)_localctx).e2 = exp(8);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExpContext(new ExpContext(_parentctx, _parentState));
						((BinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(354);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(355);
						((BinaryExpContext)_localctx).op = match(T__37);
						setState(356);
						((BinaryExpContext)_localctx).e2 = exp(7);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExpContext(new ExpContext(_parentctx, _parentState));
						((BinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(357);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(358);
						((BinaryExpContext)_localctx).op = match(T__38);
						setState(359);
						((BinaryExpContext)_localctx).e2 = exp(6);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExpContext(new ExpContext(_parentctx, _parentState));
						((BinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(360);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(361);
						((BinaryExpContext)_localctx).op = match(T__39);
						setState(362);
						((BinaryExpContext)_localctx).e2 = exp(5);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExpContext(new ExpContext(_parentctx, _parentState));
						((BinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(363);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(364);
						((BinaryExpContext)_localctx).op = match(T__40);
						setState(365);
						((BinaryExpContext)_localctx).e2 = exp(4);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExpContext(new ExpContext(_parentctx, _parentState));
						((BinaryExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(366);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(367);
						((BinaryExpContext)_localctx).op = match(T__41);
						setState(368);
						((BinaryExpContext)_localctx).e2 = exp(3);
						}
						break;
					case 11:
						{
						_localctx = new CondExpContext(new ExpContext(_parentctx, _parentState));
						((CondExpContext)_localctx).e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(369);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(370);
						match(T__42);
						setState(371);
						((CondExpContext)_localctx).e2 = exp(0);
						setState(372);
						match(T__43);
						setState(373);
						((CondExpContext)_localctx).e3 = exp(2);
						}
						break;
					}
					} 
				}
				setState(379);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ShiftOpContext extends ParserRuleContext {
		public ShiftOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shiftOp; }
	 
		public ShiftOpContext() { }
		public void copyFrom(ShiftOpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class URightShiftOpContext extends ShiftOpContext {
		public URightShiftOpContext(ShiftOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitURightShiftOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LeftShiftOpContext extends ShiftOpContext {
		public LeftShiftOpContext(ShiftOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitLeftShiftOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RightShiftOpContext extends ShiftOpContext {
		public RightShiftOpContext(ShiftOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitRightShiftOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShiftOpContext shiftOp() throws RecognitionException {
		ShiftOpContext _localctx = new ShiftOpContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_shiftOp);
		try {
			setState(387);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				_localctx = new LeftShiftOpContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(380);
				match(T__31);
				setState(381);
				match(T__31);
				}
				break;
			case 2:
				_localctx = new RightShiftOpContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(382);
				match(T__32);
				setState(383);
				match(T__32);
				}
				break;
			case 3:
				_localctx = new URightShiftOpContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(384);
				match(T__32);
				setState(385);
				match(T__32);
				setState(386);
				match(T__32);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PExpContext extends ParserRuleContext {
		public PExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pExp; }
	 
		public PExpContext() { }
		public void copyFrom(PExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrueLiteralContext extends PExpContext {
		public TrueLiteralContext(PExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitTrueLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntLiteralContext extends PExpContext {
		public Token INT;
		public TerminalNode INT() { return getToken(StaticJavaParser.INT, 0); }
		public IntLiteralContext(PExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryPrefixContext extends PExpContext {
		public PPrefixContext pPrefix() {
			return getRuleContext(PPrefixContext.class,0);
		}
		public List<PSuffixContext> pSuffix() {
			return getRuleContexts(PSuffixContext.class);
		}
		public PSuffixContext pSuffix(int i) {
			return getRuleContext(PSuffixContext.class,i);
		}
		public PrimaryPrefixContext(PExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitPrimaryPrefix(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NullLiteralContext extends PExpContext {
		public NullLiteralContext(PExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitNullLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FalseLiteralContext extends PExpContext {
		public FalseLiteralContext(PExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitFalseLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PExpContext pExp() throws RecognitionException {
		PExpContext _localctx = new PExpContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_pExp);
		try {
			int _alt;
			setState(401);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new IntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(389);
				((IntLiteralContext)_localctx).INT = match(INT);
				setState(390);
				if (!( new java.math.BigInteger((((IntLiteralContext)_localctx).INT!=null?((IntLiteralContext)_localctx).INT.getText():null)).bitLength() < 32 )) throw new FailedPredicateException(this, " new java.math.BigInteger($INT.text).bitLength() < 32 ");
				}
				break;
			case T__44:
				_localctx = new TrueLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(391);
				match(T__44);
				}
				break;
			case T__45:
				_localctx = new FalseLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(392);
				match(T__45);
				}
				break;
			case T__46:
				_localctx = new NullLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(393);
				match(T__46);
				}
				break;
			case T__7:
			case T__47:
			case ID:
				_localctx = new PrimaryPrefixContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(394);
				pPrefix();
				setState(398);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(395);
						pSuffix();
						}
						} 
					}
					setState(400);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PSuffixContext extends ParserRuleContext {
		public PSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pSuffix; }
	 
		public PSuffixContext() { }
		public void copyFrom(PSuffixContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AccessSuffixContext extends PSuffixContext {
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public AccessSuffixContext(PSuffixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitAccessSuffix(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IndexingSuffixContext extends PSuffixContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public IndexingSuffixContext(PSuffixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitIndexingSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PSuffixContext pSuffix() throws RecognitionException {
		PSuffixContext _localctx = new PSuffixContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_pSuffix);
		try {
			setState(409);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
				_localctx = new AccessSuffixContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(403);
				match(T__15);
				setState(404);
				match(ID);
				}
				break;
			case T__8:
				_localctx = new IndexingSuffixContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(405);
				match(T__8);
				setState(406);
				exp(0);
				setState(407);
				match(T__9);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PPrefixContext extends ParserRuleContext {
		public PPrefixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pPrefix; }
	 
		public PPrefixContext() { }
		public void copyFrom(PPrefixContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayBasicInitContext extends PPrefixContext {
		public BasicTypeContext basicType() {
			return getRuleContext(BasicTypeContext.class,0);
		}
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public NewArrayBasicInitContext(PPrefixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitNewArrayBasicInit(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdExpContext extends PPrefixContext {
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public IdExpContext(PPrefixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitIdExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewObjectContext extends PPrefixContext {
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public NewObjectContext(PPrefixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitNewObject(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InvokeExpContext extends PPrefixContext {
		public InvokeContext invoke() {
			return getRuleContext(InvokeContext.class,0);
		}
		public InvokeExpContext(PPrefixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitInvokeExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExpContext extends PPrefixContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ParenExpContext(PPrefixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitParenExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayBasicContext extends PPrefixContext {
		public BasicTypeContext basicType() {
			return getRuleContext(BasicTypeContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public NewArrayBasicContext(PPrefixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitNewArrayBasic(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayObjectContext extends PPrefixContext {
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public NewArrayObjectContext(PPrefixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitNewArrayObject(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayObjectInitContext extends PPrefixContext {
		public TerminalNode ID() { return getToken(StaticJavaParser.ID, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public NewArrayObjectInitContext(PPrefixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitNewArrayObjectInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PPrefixContext pPrefix() throws RecognitionException {
		PPrefixContext _localctx = new PPrefixContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_pPrefix);
		int _la;
		try {
			setState(452);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				_localctx = new ParenExpContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(411);
				match(T__7);
				setState(412);
				exp(0);
				setState(413);
				match(T__10);
				}
				break;
			case 2:
				_localctx = new InvokeExpContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(415);
				invoke();
				}
				break;
			case 3:
				_localctx = new IdExpContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(416);
				match(ID);
				}
				break;
			case 4:
				_localctx = new NewArrayObjectContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(417);
				match(T__47);
				setState(418);
				match(ID);
				setState(419);
				match(T__8);
				setState(420);
				exp(0);
				setState(421);
				match(T__9);
				}
				break;
			case 5:
				_localctx = new NewArrayObjectInitContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(423);
				match(T__47);
				setState(424);
				match(ID);
				setState(425);
				match(T__8);
				setState(426);
				match(T__9);
				setState(427);
				match(T__1);
				setState(429);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615944913152L) != 0)) {
					{
					setState(428);
					args();
					}
				}

				setState(431);
				match(T__2);
				}
				break;
			case 6:
				_localctx = new NewObjectContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(432);
				match(T__47);
				setState(433);
				match(ID);
				setState(434);
				match(T__7);
				setState(435);
				match(T__10);
				}
				break;
			case 7:
				_localctx = new NewArrayBasicContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(436);
				match(T__47);
				setState(437);
				basicType();
				setState(438);
				match(T__8);
				setState(439);
				exp(0);
				setState(440);
				match(T__9);
				}
				break;
			case 8:
				_localctx = new NewArrayBasicInitContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(442);
				match(T__47);
				setState(443);
				basicType();
				setState(444);
				match(T__8);
				setState(445);
				match(T__9);
				setState(446);
				match(T__1);
				setState(448);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615944913152L) != 0)) {
					{
					setState(447);
					args();
					}
				}

				setState(450);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InvokeContext extends ParserRuleContext {
		public Token id1;
		public Token id2;
		public List<TerminalNode> ID() { return getTokens(StaticJavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(StaticJavaParser.ID, i);
		}
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public InvokeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_invoke; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitInvoke(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InvokeContext invoke() throws RecognitionException {
		InvokeContext _localctx = new InvokeContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_invoke);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(456);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(454);
				((InvokeContext)_localctx).id1 = match(ID);
				setState(455);
				match(T__15);
				}
				break;
			}
			setState(458);
			((InvokeContext)_localctx).id2 = match(ID);
			setState(459);
			match(T__7);
			setState(461);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615944913152L) != 0)) {
				{
				setState(460);
				args();
				}
			}

			setState(463);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgsContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StaticJavaVisitor ) return ((StaticJavaVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_args);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(465);
			exp(0);
			setState(470);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13) {
				{
				{
				setState(466);
				match(T__13);
				setState(467);
				exp(0);
				}
				}
				setState(472);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return mainMethodDeclaration_sempred((MainMethodDeclarationContext)_localctx, predIndex);
		case 29:
			return exp_sempred((ExpContext)_localctx, predIndex);
		case 31:
			return pExp_sempred((PExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean mainMethodDeclaration_sempred(MainMethodDeclarationContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return  "main".equals((((MainMethodDeclarationContext)_localctx).id1!=null?((MainMethodDeclarationContext)_localctx).id1.getText():null)) ;
		case 1:
			return  "String".equals((((MainMethodDeclarationContext)_localctx).id2!=null?((MainMethodDeclarationContext)_localctx).id2.getText():null)) ;
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 11);
		case 3:
			return precpred(_ctx, 10);
		case 4:
			return precpred(_ctx, 9);
		case 5:
			return precpred(_ctx, 8);
		case 6:
			return precpred(_ctx, 7);
		case 7:
			return precpred(_ctx, 6);
		case 8:
			return precpred(_ctx, 5);
		case 9:
			return precpred(_ctx, 4);
		case 10:
			return precpred(_ctx, 3);
		case 11:
			return precpred(_ctx, 2);
		case 12:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean pExp_sempred(PExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 13:
			return  new java.math.BigInteger((((IntLiteralContext)_localctx).INT!=null?((IntLiteralContext)_localctx).INT.getText():null)).bitLength() < 32 ;
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00014\u01da\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0001\u0000\u0005\u0000J\b\u0000\n\u0000\f\u0000M\t\u0000\u0001"+
		"\u0000\u0001\u0000\u0005\u0000Q\b\u0000\n\u0000\f\u0000T\t\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005"+
		"\u0001\\\b\u0001\n\u0001\f\u0001_\t\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003n\b"+
		"\u0003\n\u0003\f\u0003q\t\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0003\u0004w\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u0093"+
		"\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0003\b\u009c\b\b\u0001\b\u0001\b\u0003\b\u00a0\b\b\u0001\t"+
		"\u0001\t\u0003\t\u00a4\b\t\u0001\n\u0001\n\u0003\n\u00a8\b\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0005\u000b\u00ad\b\u000b\n\u000b\f\u000b\u00b0"+
		"\t\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0005\r\u00b6\b\r\n\r\f\r\u00b9"+
		"\t\r\u0001\r\u0005\r\u00bc\b\r\n\r\f\r\u00bf\t\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00cd\b\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012"+
		"\u00e0\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0005\u0013\u00e8\b\u0013\n\u0013\f\u0013\u00eb\t\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00f1\b\u0013\n"+
		"\u0013\f\u0013\u00f4\t\u0013\u0001\u0013\u0003\u0013\u00f7\b\u0013\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005"+
		"\u0014\u00ff\b\u0014\n\u0014\f\u0014\u0102\t\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u0109\b\u0015\u0001\u0015"+
		"\u0001\u0015\u0003\u0015\u010d\b\u0015\u0001\u0015\u0001\u0015\u0003\u0015"+
		"\u0111\b\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u0116\b"+
		"\u0015\n\u0015\f\u0015\u0119\t\u0015\u0001\u0015\u0001\u0015\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0005\u0016\u0120\b\u0016\n\u0016\f\u0016\u0123"+
		"\t\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0005\u0017\u0128\b\u0017"+
		"\n\u0017\f\u0017\u012b\t\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0005\u0019\u0133\b\u0019\n\u0019\f\u0019"+
		"\u0136\t\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0003\u001c\u0147\b\u001c"+
		"\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0003\u001d\u0151\b\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0005\u001d"+
		"\u0178\b\u001d\n\u001d\f\u001d\u017b\t\u001d\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u0184"+
		"\b\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0005\u001f\u018d\b\u001f\n\u001f\f\u001f\u0190\t\u001f"+
		"\u0003\u001f\u0192\b\u001f\u0001 \u0001 \u0001 \u0001 \u0001 \u0001 \u0003"+
		" \u019a\b \u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001"+
		"!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0003"+
		"!\u01ae\b!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001"+
		"!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0003!\u01c1"+
		"\b!\u0001!\u0001!\u0003!\u01c5\b!\u0001\"\u0001\"\u0003\"\u01c9\b\"\u0001"+
		"\"\u0001\"\u0001\"\u0003\"\u01ce\b\"\u0001\"\u0001\"\u0001#\u0001#\u0001"+
		"#\u0005#\u01d5\b#\n#\f#\u01d8\t#\u0001#\u0000\u0001:$\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$&(*,.02468:<>@BDF\u0000\u0006\u0001\u0000\u0015\u0016\u0001\u0000\u0019"+
		"\u001a\u0001\u0000\u001b\u001c\u0001\u0000\u001d\u001f\u0001\u0000 #\u0001"+
		"\u0000$%\u01f8\u0000K\u0001\u0000\u0000\u0000\u0002W\u0001\u0000\u0000"+
		"\u0000\u0004b\u0001\u0000\u0000\u0000\u0006g\u0001\u0000\u0000\u0000\b"+
		"v\u0001\u0000\u0000\u0000\nx\u0001\u0000\u0000\u0000\f\u0088\u0001\u0000"+
		"\u0000\u0000\u000e\u008d\u0001\u0000\u0000\u0000\u0010\u009b\u0001\u0000"+
		"\u0000\u0000\u0012\u00a3\u0001\u0000\u0000\u0000\u0014\u00a7\u0001\u0000"+
		"\u0000\u0000\u0016\u00a9\u0001\u0000\u0000\u0000\u0018\u00b1\u0001\u0000"+
		"\u0000\u0000\u001a\u00b7\u0001\u0000\u0000\u0000\u001c\u00c0\u0001\u0000"+
		"\u0000\u0000\u001e\u00cc\u0001\u0000\u0000\u0000 \u00ce\u0001\u0000\u0000"+
		"\u0000\"\u00d1\u0001\u0000\u0000\u0000$\u00df\u0001\u0000\u0000\u0000"+
		"&\u00e1\u0001\u0000\u0000\u0000(\u00f8\u0001\u0000\u0000\u0000*\u0105"+
		"\u0001\u0000\u0000\u0000,\u011c\u0001\u0000\u0000\u0000.\u0124\u0001\u0000"+
		"\u0000\u00000\u012c\u0001\u0000\u0000\u00002\u012f\u0001\u0000\u0000\u0000"+
		"4\u013e\u0001\u0000\u0000\u00006\u0141\u0001\u0000\u0000\u00008\u0144"+
		"\u0001\u0000\u0000\u0000:\u0150\u0001\u0000\u0000\u0000<\u0183\u0001\u0000"+
		"\u0000\u0000>\u0191\u0001\u0000\u0000\u0000@\u0199\u0001\u0000\u0000\u0000"+
		"B\u01c4\u0001\u0000\u0000\u0000D\u01c8\u0001\u0000\u0000\u0000F\u01d1"+
		"\u0001\u0000\u0000\u0000HJ\u0003\u0002\u0001\u0000IH\u0001\u0000\u0000"+
		"\u0000JM\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000KL\u0001\u0000"+
		"\u0000\u0000LN\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000NR\u0003"+
		"\u0006\u0003\u0000OQ\u0003\u0002\u0001\u0000PO\u0001\u0000\u0000\u0000"+
		"QT\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000RS\u0001\u0000\u0000"+
		"\u0000SU\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000UV\u0005\u0000"+
		"\u0000\u0001V\u0001\u0001\u0000\u0000\u0000WX\u0005\u0001\u0000\u0000"+
		"XY\u00051\u0000\u0000Y]\u0005\u0002\u0000\u0000Z\\\u0003\u0004\u0002\u0000"+
		"[Z\u0001\u0000\u0000\u0000\\_\u0001\u0000\u0000\u0000][\u0001\u0000\u0000"+
		"\u0000]^\u0001\u0000\u0000\u0000^`\u0001\u0000\u0000\u0000_]\u0001\u0000"+
		"\u0000\u0000`a\u0005\u0003\u0000\u0000a\u0003\u0001\u0000\u0000\u0000"+
		"bc\u0005\u0004\u0000\u0000cd\u0003\u0010\b\u0000de\u00051\u0000\u0000"+
		"ef\u0005\u0005\u0000\u0000f\u0005\u0001\u0000\u0000\u0000gh\u0005\u0004"+
		"\u0000\u0000hi\u0005\u0001\u0000\u0000ij\u00051\u0000\u0000jk\u0005\u0002"+
		"\u0000\u0000ko\u0003\n\u0005\u0000ln\u0003\b\u0004\u0000ml\u0001\u0000"+
		"\u0000\u0000nq\u0001\u0000\u0000\u0000om\u0001\u0000\u0000\u0000op\u0001"+
		"\u0000\u0000\u0000pr\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000"+
		"rs\u0005\u0003\u0000\u0000s\u0007\u0001\u0000\u0000\u0000tw\u0003\f\u0006"+
		"\u0000uw\u0003\u000e\u0007\u0000vt\u0001\u0000\u0000\u0000vu\u0001\u0000"+
		"\u0000\u0000w\t\u0001\u0000\u0000\u0000xy\u0005\u0004\u0000\u0000yz\u0005"+
		"\u0006\u0000\u0000z{\u0005\u0007\u0000\u0000{|\u00051\u0000\u0000|}\u0004"+
		"\u0005\u0000\u0001}~\u0005\b\u0000\u0000~\u007f\u00051\u0000\u0000\u007f"+
		"\u0080\u0004\u0005\u0001\u0001\u0080\u0081\u0005\t\u0000\u0000\u0081\u0082"+
		"\u0005\n\u0000\u0000\u0082\u0083\u00051\u0000\u0000\u0083\u0084\u0005"+
		"\u000b\u0000\u0000\u0084\u0085\u0005\u0002\u0000\u0000\u0085\u0086\u0003"+
		"\u001a\r\u0000\u0086\u0087\u0005\u0003\u0000\u0000\u0087\u000b\u0001\u0000"+
		"\u0000\u0000\u0088\u0089\u0005\u0006\u0000\u0000\u0089\u008a\u0003\u0010"+
		"\b\u0000\u008a\u008b\u00051\u0000\u0000\u008b\u008c\u0005\u0005\u0000"+
		"\u0000\u008c\r\u0001\u0000\u0000\u0000\u008d\u008e\u0005\u0006\u0000\u0000"+
		"\u008e\u008f\u0003\u0014\n\u0000\u008f\u0090\u00051\u0000\u0000\u0090"+
		"\u0092\u0005\b\u0000\u0000\u0091\u0093\u0003\u0016\u000b\u0000\u0092\u0091"+
		"\u0001\u0000\u0000\u0000\u0092\u0093\u0001\u0000\u0000\u0000\u0093\u0094"+
		"\u0001\u0000\u0000\u0000\u0094\u0095\u0005\u000b\u0000\u0000\u0095\u0096"+
		"\u0005\u0002\u0000\u0000\u0096\u0097\u0003\u001a\r\u0000\u0097\u0098\u0005"+
		"\u0003\u0000\u0000\u0098\u000f\u0001\u0000\u0000\u0000\u0099\u009c\u0003"+
		"\u0012\t\u0000\u009a\u009c\u00051\u0000\u0000\u009b\u0099\u0001\u0000"+
		"\u0000\u0000\u009b\u009a\u0001\u0000\u0000\u0000\u009c\u009f\u0001\u0000"+
		"\u0000\u0000\u009d\u009e\u0005\t\u0000\u0000\u009e\u00a0\u0005\n\u0000"+
		"\u0000\u009f\u009d\u0001\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000"+
		"\u0000\u00a0\u0011\u0001\u0000\u0000\u0000\u00a1\u00a4\u0005\f\u0000\u0000"+
		"\u00a2\u00a4\u0005\r\u0000\u0000\u00a3\u00a1\u0001\u0000\u0000\u0000\u00a3"+
		"\u00a2\u0001\u0000\u0000\u0000\u00a4\u0013\u0001\u0000\u0000\u0000\u00a5"+
		"\u00a8\u0005\u0007\u0000\u0000\u00a6\u00a8\u0003\u0010\b\u0000\u00a7\u00a5"+
		"\u0001\u0000\u0000\u0000\u00a7\u00a6\u0001\u0000\u0000\u0000\u00a8\u0015"+
		"\u0001\u0000\u0000\u0000\u00a9\u00ae\u0003\u0018\f\u0000\u00aa\u00ab\u0005"+
		"\u000e\u0000\u0000\u00ab\u00ad\u0003\u0018\f\u0000\u00ac\u00aa\u0001\u0000"+
		"\u0000\u0000\u00ad\u00b0\u0001\u0000\u0000\u0000\u00ae\u00ac\u0001\u0000"+
		"\u0000\u0000\u00ae\u00af\u0001\u0000\u0000\u0000\u00af\u0017\u0001\u0000"+
		"\u0000\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000\u00b1\u00b2\u0003\u0010"+
		"\b\u0000\u00b2\u00b3\u00051\u0000\u0000\u00b3\u0019\u0001\u0000\u0000"+
		"\u0000\u00b4\u00b6\u0003\u001c\u000e\u0000\u00b5\u00b4\u0001\u0000\u0000"+
		"\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000\u00b7\u00b5\u0001\u0000\u0000"+
		"\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u00bd\u0001\u0000\u0000"+
		"\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00ba\u00bc\u0003\u001e\u000f"+
		"\u0000\u00bb\u00ba\u0001\u0000\u0000\u0000\u00bc\u00bf\u0001\u0000\u0000"+
		"\u0000\u00bd\u00bb\u0001\u0000\u0000\u0000\u00bd\u00be\u0001\u0000\u0000"+
		"\u0000\u00be\u001b\u0001\u0000\u0000\u0000\u00bf\u00bd\u0001\u0000\u0000"+
		"\u0000\u00c0\u00c1\u0003\u0010\b\u0000\u00c1\u00c2\u00051\u0000\u0000"+
		"\u00c2\u00c3\u0005\u0005\u0000\u0000\u00c3\u001d\u0001\u0000\u0000\u0000"+
		"\u00c4\u00cd\u0003 \u0010\u0000\u00c5\u00cd\u0003&\u0013\u0000\u00c6\u00cd"+
		"\u0003(\u0014\u0000\u00c7\u00cd\u00032\u0019\u0000\u00c8\u00cd\u0003*"+
		"\u0015\u0000\u00c9\u00cd\u00034\u001a\u0000\u00ca\u00cd\u00036\u001b\u0000"+
		"\u00cb\u00cd\u00038\u001c\u0000\u00cc\u00c4\u0001\u0000\u0000\u0000\u00cc"+
		"\u00c5\u0001\u0000\u0000\u0000\u00cc\u00c6\u0001\u0000\u0000\u0000\u00cc"+
		"\u00c7\u0001\u0000\u0000\u0000\u00cc\u00c8\u0001\u0000\u0000\u0000\u00cc"+
		"\u00c9\u0001\u0000\u0000\u0000\u00cc\u00ca\u0001\u0000\u0000\u0000\u00cc"+
		"\u00cb\u0001\u0000\u0000\u0000\u00cd\u001f\u0001\u0000\u0000\u0000\u00ce"+
		"\u00cf\u0003\"\u0011\u0000\u00cf\u00d0\u0005\u0005\u0000\u0000\u00d0!"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d2\u0003$\u0012\u0000\u00d2\u00d3\u0005"+
		"\u000f\u0000\u0000\u00d3\u00d4\u0003:\u001d\u0000\u00d4#\u0001\u0000\u0000"+
		"\u0000\u00d5\u00e0\u00051\u0000\u0000\u00d6\u00d7\u0003:\u001d\u0000\u00d7"+
		"\u00d8\u0005\u0010\u0000\u0000\u00d8\u00d9\u00051\u0000\u0000\u00d9\u00e0"+
		"\u0001\u0000\u0000\u0000\u00da\u00db\u0003:\u001d\u0000\u00db\u00dc\u0005"+
		"\t\u0000\u0000\u00dc\u00dd\u0003:\u001d\u0000\u00dd\u00de\u0005\n\u0000"+
		"\u0000\u00de\u00e0\u0001\u0000\u0000\u0000\u00df\u00d5\u0001\u0000\u0000"+
		"\u0000\u00df\u00d6\u0001\u0000\u0000\u0000\u00df\u00da\u0001\u0000\u0000"+
		"\u0000\u00e0%\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005\u0011\u0000\u0000"+
		"\u00e2\u00e3\u0005\b\u0000\u0000\u00e3\u00e4\u0003:\u001d\u0000\u00e4"+
		"\u00e5\u0005\u000b\u0000\u0000\u00e5\u00e9\u0005\u0002\u0000\u0000\u00e6"+
		"\u00e8\u0003\u001e\u000f\u0000\u00e7\u00e6\u0001\u0000\u0000\u0000\u00e8"+
		"\u00eb\u0001\u0000\u0000\u0000\u00e9\u00e7\u0001\u0000\u0000\u0000\u00e9"+
		"\u00ea\u0001\u0000\u0000\u0000\u00ea\u00ec\u0001\u0000\u0000\u0000\u00eb"+
		"\u00e9\u0001\u0000\u0000\u0000\u00ec\u00f6\u0005\u0003\u0000\u0000\u00ed"+
		"\u00ee\u0005\u0012\u0000\u0000\u00ee\u00f2\u0005\u0002\u0000\u0000\u00ef"+
		"\u00f1\u0003\u001e\u000f\u0000\u00f0\u00ef\u0001\u0000\u0000\u0000\u00f1"+
		"\u00f4\u0001\u0000\u0000\u0000\u00f2\u00f0\u0001\u0000\u0000\u0000\u00f2"+
		"\u00f3\u0001\u0000\u0000\u0000\u00f3\u00f5\u0001\u0000\u0000\u0000\u00f4"+
		"\u00f2\u0001\u0000\u0000\u0000\u00f5\u00f7\u0005\u0003\u0000\u0000\u00f6"+
		"\u00ed\u0001\u0000\u0000\u0000\u00f6\u00f7\u0001\u0000\u0000\u0000\u00f7"+
		"\'\u0001\u0000\u0000\u0000\u00f8\u00f9\u0005\u0013\u0000\u0000\u00f9\u00fa"+
		"\u0005\b\u0000\u0000\u00fa\u00fb\u0003:\u001d\u0000\u00fb\u00fc\u0005"+
		"\u000b\u0000\u0000\u00fc\u0100\u0005\u0002\u0000\u0000\u00fd\u00ff\u0003"+
		"\u001e\u000f\u0000\u00fe\u00fd\u0001\u0000\u0000\u0000\u00ff\u0102\u0001"+
		"\u0000\u0000\u0000\u0100\u00fe\u0001\u0000\u0000\u0000\u0100\u0101\u0001"+
		"\u0000\u0000\u0000\u0101\u0103\u0001\u0000\u0000\u0000\u0102\u0100\u0001"+
		"\u0000\u0000\u0000\u0103\u0104\u0005\u0003\u0000\u0000\u0104)\u0001\u0000"+
		"\u0000\u0000\u0105\u0106\u0005\u0014\u0000\u0000\u0106\u0108\u0005\b\u0000"+
		"\u0000\u0107\u0109\u0003,\u0016\u0000\u0108\u0107\u0001\u0000\u0000\u0000"+
		"\u0108\u0109\u0001\u0000\u0000\u0000\u0109\u010a\u0001\u0000\u0000\u0000"+
		"\u010a\u010c\u0005\u0005\u0000\u0000\u010b\u010d\u0003:\u001d\u0000\u010c"+
		"\u010b\u0001\u0000\u0000\u0000\u010c\u010d\u0001\u0000\u0000\u0000\u010d"+
		"\u010e\u0001\u0000\u0000\u0000\u010e\u0110\u0005\u0005\u0000\u0000\u010f"+
		"\u0111\u0003.\u0017\u0000\u0110\u010f\u0001\u0000\u0000\u0000\u0110\u0111"+
		"\u0001\u0000\u0000\u0000\u0111\u0112\u0001\u0000\u0000\u0000\u0112\u0113"+
		"\u0005\u000b\u0000\u0000\u0113\u0117\u0005\u0002\u0000\u0000\u0114\u0116"+
		"\u0003\u001e\u000f\u0000\u0115\u0114\u0001\u0000\u0000\u0000\u0116\u0119"+
		"\u0001\u0000\u0000\u0000\u0117\u0115\u0001\u0000\u0000\u0000\u0117\u0118"+
		"\u0001\u0000\u0000\u0000\u0118\u011a\u0001\u0000\u0000\u0000\u0119\u0117"+
		"\u0001\u0000\u0000\u0000\u011a\u011b\u0005\u0003\u0000\u0000\u011b+\u0001"+
		"\u0000\u0000\u0000\u011c\u0121\u0003\"\u0011\u0000\u011d\u011e\u0005\u000e"+
		"\u0000\u0000\u011e\u0120\u0003\"\u0011\u0000\u011f\u011d\u0001\u0000\u0000"+
		"\u0000\u0120\u0123\u0001\u0000\u0000\u0000\u0121\u011f\u0001\u0000\u0000"+
		"\u0000\u0121\u0122\u0001\u0000\u0000\u0000\u0122-\u0001\u0000\u0000\u0000"+
		"\u0123\u0121\u0001\u0000\u0000\u0000\u0124\u0129\u00030\u0018\u0000\u0125"+
		"\u0126\u0005\u000e\u0000\u0000\u0126\u0128\u00030\u0018\u0000\u0127\u0125"+
		"\u0001\u0000\u0000\u0000\u0128\u012b\u0001\u0000\u0000\u0000\u0129\u0127"+
		"\u0001\u0000\u0000\u0000\u0129\u012a\u0001\u0000\u0000\u0000\u012a/\u0001"+
		"\u0000\u0000\u0000\u012b\u0129\u0001\u0000\u0000\u0000\u012c\u012d\u0003"+
		"$\u0012\u0000\u012d\u012e\u0007\u0000\u0000\u0000\u012e1\u0001\u0000\u0000"+
		"\u0000\u012f\u0130\u0005\u0017\u0000\u0000\u0130\u0134\u0005\u0002\u0000"+
		"\u0000\u0131\u0133\u0003\u001e\u000f\u0000\u0132\u0131\u0001\u0000\u0000"+
		"\u0000\u0133\u0136\u0001\u0000\u0000\u0000\u0134\u0132\u0001\u0000\u0000"+
		"\u0000\u0134\u0135\u0001\u0000\u0000\u0000\u0135\u0137\u0001\u0000\u0000"+
		"\u0000\u0136\u0134\u0001\u0000\u0000\u0000\u0137\u0138\u0005\u0003\u0000"+
		"\u0000\u0138\u0139\u0005\u0013\u0000\u0000\u0139\u013a\u0005\b\u0000\u0000"+
		"\u013a\u013b\u0003:\u001d\u0000\u013b\u013c\u0005\u000b\u0000\u0000\u013c"+
		"\u013d\u0005\u0005\u0000\u0000\u013d3\u0001\u0000\u0000\u0000\u013e\u013f"+
		"\u00030\u0018\u0000\u013f\u0140\u0005\u0005\u0000\u0000\u01405\u0001\u0000"+
		"\u0000\u0000\u0141\u0142\u0003D\"\u0000\u0142\u0143\u0005\u0005\u0000"+
		"\u0000\u01437\u0001\u0000\u0000\u0000\u0144\u0146\u0005\u0018\u0000\u0000"+
		"\u0145\u0147\u0003:\u001d\u0000\u0146\u0145\u0001\u0000\u0000\u0000\u0146"+
		"\u0147\u0001\u0000\u0000\u0000\u0147\u0148\u0001\u0000\u0000\u0000\u0148"+
		"\u0149\u0005\u0005\u0000\u0000\u01499\u0001\u0000\u0000\u0000\u014a\u014b"+
		"\u0006\u001d\uffff\uffff\u0000\u014b\u0151\u0003>\u001f\u0000\u014c\u014d"+
		"\u0007\u0001\u0000\u0000\u014d\u0151\u0003:\u001d\r\u014e\u014f\u0007"+
		"\u0002\u0000\u0000\u014f\u0151\u0003:\u001d\f\u0150\u014a\u0001\u0000"+
		"\u0000\u0000\u0150\u014c\u0001\u0000\u0000\u0000\u0150\u014e\u0001\u0000"+
		"\u0000\u0000\u0151\u0179\u0001\u0000\u0000\u0000\u0152\u0153\n\u000b\u0000"+
		"\u0000\u0153\u0154\u0007\u0003\u0000\u0000\u0154\u0178\u0003:\u001d\f"+
		"\u0155\u0156\n\n\u0000\u0000\u0156\u0157\u0007\u0001\u0000\u0000\u0157"+
		"\u0178\u0003:\u001d\u000b\u0158\u0159\n\t\u0000\u0000\u0159\u015a\u0003"+
		"<\u001e\u0000\u015a\u015b\u0003:\u001d\n\u015b\u0178\u0001\u0000\u0000"+
		"\u0000\u015c\u015d\n\b\u0000\u0000\u015d\u015e\u0007\u0004\u0000\u0000"+
		"\u015e\u0178\u0003:\u001d\t\u015f\u0160\n\u0007\u0000\u0000\u0160\u0161"+
		"\u0007\u0005\u0000\u0000\u0161\u0178\u0003:\u001d\b\u0162\u0163\n\u0006"+
		"\u0000\u0000\u0163\u0164\u0005&\u0000\u0000\u0164\u0178\u0003:\u001d\u0007"+
		"\u0165\u0166\n\u0005\u0000\u0000\u0166\u0167\u0005\'\u0000\u0000\u0167"+
		"\u0178\u0003:\u001d\u0006\u0168\u0169\n\u0004\u0000\u0000\u0169\u016a"+
		"\u0005(\u0000\u0000\u016a\u0178\u0003:\u001d\u0005\u016b\u016c\n\u0003"+
		"\u0000\u0000\u016c\u016d\u0005)\u0000\u0000\u016d\u0178\u0003:\u001d\u0004"+
		"\u016e\u016f\n\u0002\u0000\u0000\u016f\u0170\u0005*\u0000\u0000\u0170"+
		"\u0178\u0003:\u001d\u0003\u0171\u0172\n\u0001\u0000\u0000\u0172\u0173"+
		"\u0005+\u0000\u0000\u0173\u0174\u0003:\u001d\u0000\u0174\u0175\u0005,"+
		"\u0000\u0000\u0175\u0176\u0003:\u001d\u0002\u0176\u0178\u0001\u0000\u0000"+
		"\u0000\u0177\u0152\u0001\u0000\u0000\u0000\u0177\u0155\u0001\u0000\u0000"+
		"\u0000\u0177\u0158\u0001\u0000\u0000\u0000\u0177\u015c\u0001\u0000\u0000"+
		"\u0000\u0177\u015f\u0001\u0000\u0000\u0000\u0177\u0162\u0001\u0000\u0000"+
		"\u0000\u0177\u0165\u0001\u0000\u0000\u0000\u0177\u0168\u0001\u0000\u0000"+
		"\u0000\u0177\u016b\u0001\u0000\u0000\u0000\u0177\u016e\u0001\u0000\u0000"+
		"\u0000\u0177\u0171\u0001\u0000\u0000\u0000\u0178\u017b\u0001\u0000\u0000"+
		"\u0000\u0179\u0177\u0001\u0000\u0000\u0000\u0179\u017a\u0001\u0000\u0000"+
		"\u0000\u017a;\u0001\u0000\u0000\u0000\u017b\u0179\u0001\u0000\u0000\u0000"+
		"\u017c\u017d\u0005 \u0000\u0000\u017d\u0184\u0005 \u0000\u0000\u017e\u017f"+
		"\u0005!\u0000\u0000\u017f\u0184\u0005!\u0000\u0000\u0180\u0181\u0005!"+
		"\u0000\u0000\u0181\u0182\u0005!\u0000\u0000\u0182\u0184\u0005!\u0000\u0000"+
		"\u0183\u017c\u0001\u0000\u0000\u0000\u0183\u017e\u0001\u0000\u0000\u0000"+
		"\u0183\u0180\u0001\u0000\u0000\u0000\u0184=\u0001\u0000\u0000\u0000\u0185"+
		"\u0186\u00052\u0000\u0000\u0186\u0192\u0004\u001f\r\u0001\u0187\u0192"+
		"\u0005-\u0000\u0000\u0188\u0192\u0005.\u0000\u0000\u0189\u0192\u0005/"+
		"\u0000\u0000\u018a\u018e\u0003B!\u0000\u018b\u018d\u0003@ \u0000\u018c"+
		"\u018b\u0001\u0000\u0000\u0000\u018d\u0190\u0001\u0000\u0000\u0000\u018e"+
		"\u018c\u0001\u0000\u0000\u0000\u018e\u018f\u0001\u0000\u0000\u0000\u018f"+
		"\u0192\u0001\u0000\u0000\u0000\u0190\u018e\u0001\u0000\u0000\u0000\u0191"+
		"\u0185\u0001\u0000\u0000\u0000\u0191\u0187\u0001\u0000\u0000\u0000\u0191"+
		"\u0188\u0001\u0000\u0000\u0000\u0191\u0189\u0001\u0000\u0000\u0000\u0191"+
		"\u018a\u0001\u0000\u0000\u0000\u0192?\u0001\u0000\u0000\u0000\u0193\u0194"+
		"\u0005\u0010\u0000\u0000\u0194\u019a\u00051\u0000\u0000\u0195\u0196\u0005"+
		"\t\u0000\u0000\u0196\u0197\u0003:\u001d\u0000\u0197\u0198\u0005\n\u0000"+
		"\u0000\u0198\u019a\u0001\u0000\u0000\u0000\u0199\u0193\u0001\u0000\u0000"+
		"\u0000\u0199\u0195\u0001\u0000\u0000\u0000\u019aA\u0001\u0000\u0000\u0000"+
		"\u019b\u019c\u0005\b\u0000\u0000\u019c\u019d\u0003:\u001d\u0000\u019d"+
		"\u019e\u0005\u000b\u0000\u0000\u019e\u01c5\u0001\u0000\u0000\u0000\u019f"+
		"\u01c5\u0003D\"\u0000\u01a0\u01c5\u00051\u0000\u0000\u01a1\u01a2\u0005"+
		"0\u0000\u0000\u01a2\u01a3\u00051\u0000\u0000\u01a3\u01a4\u0005\t\u0000"+
		"\u0000\u01a4\u01a5\u0003:\u001d\u0000\u01a5\u01a6\u0005\n\u0000\u0000"+
		"\u01a6\u01c5\u0001\u0000\u0000\u0000\u01a7\u01a8\u00050\u0000\u0000\u01a8"+
		"\u01a9\u00051\u0000\u0000\u01a9\u01aa\u0005\t\u0000\u0000\u01aa\u01ab"+
		"\u0005\n\u0000\u0000\u01ab\u01ad\u0005\u0002\u0000\u0000\u01ac\u01ae\u0003"+
		"F#\u0000\u01ad\u01ac\u0001\u0000\u0000\u0000\u01ad\u01ae\u0001\u0000\u0000"+
		"\u0000\u01ae\u01af\u0001\u0000\u0000\u0000\u01af\u01c5\u0005\u0003\u0000"+
		"\u0000\u01b0\u01b1\u00050\u0000\u0000\u01b1\u01b2\u00051\u0000\u0000\u01b2"+
		"\u01b3\u0005\b\u0000\u0000\u01b3\u01c5\u0005\u000b\u0000\u0000\u01b4\u01b5"+
		"\u00050\u0000\u0000\u01b5\u01b6\u0003\u0012\t\u0000\u01b6\u01b7\u0005"+
		"\t\u0000\u0000\u01b7\u01b8\u0003:\u001d\u0000\u01b8\u01b9\u0005\n\u0000"+
		"\u0000\u01b9\u01c5\u0001\u0000\u0000\u0000\u01ba\u01bb\u00050\u0000\u0000"+
		"\u01bb\u01bc\u0003\u0012\t\u0000\u01bc\u01bd\u0005\t\u0000\u0000\u01bd"+
		"\u01be\u0005\n\u0000\u0000\u01be\u01c0\u0005\u0002\u0000\u0000\u01bf\u01c1"+
		"\u0003F#\u0000\u01c0\u01bf\u0001\u0000\u0000\u0000\u01c0\u01c1\u0001\u0000"+
		"\u0000\u0000\u01c1\u01c2\u0001\u0000\u0000\u0000\u01c2\u01c3\u0005\u0003"+
		"\u0000\u0000\u01c3\u01c5\u0001\u0000\u0000\u0000\u01c4\u019b\u0001\u0000"+
		"\u0000\u0000\u01c4\u019f\u0001\u0000\u0000\u0000\u01c4\u01a0\u0001\u0000"+
		"\u0000\u0000\u01c4\u01a1\u0001\u0000\u0000\u0000\u01c4\u01a7\u0001\u0000"+
		"\u0000\u0000\u01c4\u01b0\u0001\u0000\u0000\u0000\u01c4\u01b4\u0001\u0000"+
		"\u0000\u0000\u01c4\u01ba\u0001\u0000\u0000\u0000\u01c5C\u0001\u0000\u0000"+
		"\u0000\u01c6\u01c7\u00051\u0000\u0000\u01c7\u01c9\u0005\u0010\u0000\u0000"+
		"\u01c8\u01c6\u0001\u0000\u0000\u0000\u01c8\u01c9\u0001\u0000\u0000\u0000"+
		"\u01c9\u01ca\u0001\u0000\u0000\u0000\u01ca\u01cb\u00051\u0000\u0000\u01cb"+
		"\u01cd\u0005\b\u0000\u0000\u01cc\u01ce\u0003F#\u0000\u01cd\u01cc\u0001"+
		"\u0000\u0000\u0000\u01cd\u01ce\u0001\u0000\u0000\u0000\u01ce\u01cf\u0001"+
		"\u0000\u0000\u0000\u01cf\u01d0\u0005\u000b\u0000\u0000\u01d0E\u0001\u0000"+
		"\u0000\u0000\u01d1\u01d6\u0003:\u001d\u0000\u01d2\u01d3\u0005\u000e\u0000"+
		"\u0000\u01d3\u01d5\u0003:\u001d\u0000\u01d4\u01d2\u0001\u0000\u0000\u0000"+
		"\u01d5\u01d8\u0001\u0000\u0000\u0000\u01d6\u01d4\u0001\u0000\u0000\u0000"+
		"\u01d6\u01d7\u0001\u0000\u0000\u0000\u01d7G\u0001\u0000\u0000\u0000\u01d8"+
		"\u01d6\u0001\u0000\u0000\u0000(KR]ov\u0092\u009b\u009f\u00a3\u00a7\u00ae"+
		"\u00b7\u00bd\u00cc\u00df\u00e9\u00f2\u00f6\u0100\u0108\u010c\u0110\u0117"+
		"\u0121\u0129\u0134\u0146\u0150\u0177\u0179\u0183\u018e\u0191\u0199\u01ad"+
		"\u01c0\u01c4\u01c8\u01cd\u01d6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}