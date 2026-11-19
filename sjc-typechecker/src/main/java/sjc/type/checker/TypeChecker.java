package sjc.type.checker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
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
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;

import sjc.annotation.NonNull;
import sjc.annotation.NonNullElements;
import sjc.annotation.ReadOnly;
import sjc.annotation.ReadOnlyElements;
import sjc.symboltable.SymbolTable;
import sjc.type.ArrayType;
import sjc.type.BaseType;
import sjc.type.ClassType;
import sjc.type.NonPrimitiveType;
import sjc.type.NullType;
import sjc.type.Type;
import sjc.type.TypeFactory;
import sjc.util.Pair;

/**
 * This class is used to type check a StaticJava {@link CompilationUnit} with a
 * given {@link SymbolTable}.
 *
 */
public class TypeChecker {
  public static class Error extends RuntimeException {
    private static final long serialVersionUID = 5322515711212690043L;
    public final @NonNull @ReadOnly ASTNode node;
    public Error(@NonNull final ASTNode node, @NonNull final String message) {
      super(message);
      this.node = node;
    }
  }

  /** Visitor for type checking an ExtendedStaticJava compilation unit. */
  protected static class Visitor extends ASTVisitor {
    protected Map<ASTNode, Object> symbolMap;
    public Map<ASTNode, Type> resultTypeMap = new HashMap<>();
    public Map<Object, Pair<Type, List<Type>>> resultMethodTypeMap =
        new HashMap<>();
    protected TypeFactory tf;
    protected ArrayType stringArrayType;
    protected String className;
    protected Type methodReturnType, resultingType;
    protected @NonNullElements
    @ReadOnlyElements
    Map<String, TypeDeclaration> classMap;
    protected @NonNullElements
    @ReadOnlyElements
    Map<Pair<String, String>, FieldDeclaration> fieldMap;
    protected Visitor(final TypeFactory tf, final SymbolTable st) {
      this.tf = tf;
      this.symbolMap = st.symbolMap;
      this.stringArrayType =
          tf.getArrayType(tf.getClassType("java.lang.String"));
      this.classMap = st.classMap;
      this.fieldMap = st.fieldMap;
      for (final Pair<String, String> p : st.fieldMap.keySet()) {
        final FieldDeclaration fd = st.fieldMap.get(p);
        final Type t = convertType(fd, fd.getType());
        final ClassType ct = tf.getClassType(p.first);
        ct.fieldTypeMap.put(p.second, t);
      }
    }
    protected Type getResult() {
      Type t = resultingType;
      resultingType = null;
      return t;
    }
    protected void setResult(Expression n, Type t) {
      resultTypeMap.put(n, t);
      resultingType = t;
    }
    protected Type convertType(ASTNode n, Class c) {
      if (c == void.class)
        return tf.Void;
      if (c == boolean.class)
        return tf.Boolean;
      if (c == int.class)
        return tf.Int;
      if (c == String[].class)
        return stringArrayType;
      if (!c.isArray())
        return tf.getClassType(c.getName());
      return tf.getArrayType((BaseType) convertType(n, c.getComponentType()));
    }
    protected Type convertType(ASTNode n, org.eclipse.jdt.core.dom.Type t) {
      if (t instanceof PrimitiveType) {
        PrimitiveType.Code c = ((PrimitiveType) t).getPrimitiveTypeCode();
        if (c == PrimitiveType.BOOLEAN)
          return tf.Boolean;
        if (c == PrimitiveType.INT)
          return tf.Int;
        if (c == PrimitiveType.VOID)
          return tf.Void;
      }
      if (t instanceof SimpleType) {
        String s = ((SimpleType) t).getName().getFullyQualifiedName();
        if (classMap.containsKey(s))
          return tf.getClassType(s);
        if ("String".equals(s) || "java.lang.String".equals(s))
          return stringArrayType.baseType;
      }
      if (t instanceof org.eclipse.jdt.core.dom.ArrayType)
        return tf.getArrayType((BaseType) convertType(
            n, ((org.eclipse.jdt.core.dom.ArrayType) t).getElementType()));
      throw new Error(n, "Unexpected Type: '" + t + "'");
    }
    protected void dispose() {
      symbolMap = null;
      resultTypeMap = null;
      resultMethodTypeMap = null;
      tf = null;
      stringArrayType = null;
      className = null;
      methodReturnType = resultingType = null;
      classMap = null;
      fieldMap = null;
    }
    protected Method resolveMethod(
        MethodInvocation n, String c, String m, Type[] a) {
      try {
        Class[] p = new Class[a.length];
        for (int i = 0; i < a.length; i++) p[i] = convertClass(n, a[i]);
        Method r = Class.forName(c).getMethod(m, p);
        symbolMap.put(n, r);
        return r;
      } catch (Exception e) {
        throw new Error(n, "Unresolvable method \"" + c + "." + m + "\"");
      }
    }
    protected Class convertClass(ASTNode n, Type t) {
      if (t == tf.Void)
        return void.class;
      if (t == tf.Boolean)
        return boolean.class;
      if (t == tf.Int)
        return int.class;
      if (t == stringArrayType)
        return String[].class;
      if (t instanceof ClassType)
        try {
          return Class.forName(((ClassType) t).name);
        } catch (Exception e) {
        }
      throw new Error(n, "Unresolvable Type: '" + t + "'");
    }
    protected void typeCheckMethodInvocation(
        MethodInvocation n, String c, String m, Type[] a, Method d) {
      if (a.length != d.getParameterTypes().length)
        throw new Error(n,
            "Wrong number of arguments to invoke method \"" + m + "\" in \"" + n
                + "\"");
      List<Type> p = new ArrayList<>();
      for (int i = 0; i < a.length; i++) {
        Type t = convertType(n, d.getParameterTypes()[i]);
        if (!((a[i] instanceof NullType) && (t instanceof NonPrimitiveType))
            && t != a[i])
          throw new Error(
              n, "Type mismatch the " + i + " argument in \"" + n + "\"");
        p.add(t);
      }
      Type r = convertType(n, d.getReturnType());
      if (!resultMethodTypeMap.containsKey(d))
        resultMethodTypeMap.put(d, new Pair<Type, List<Type>>(r, p));
      setResult(n, r);
    }
    protected void typeCheckMethodInvocation(
        MethodInvocation n, String c, String m, Type[] a, MethodDeclaration d) {
      if (a.length != d.parameters().size())
        throw new Error(n,
            "Wrong number of arguments to invoke method \"" + m + "\" in \"" + n
                + "\"");
      for (int i = 0; i < a.length; i++)
        if (!((a[i] instanceof NullType)
                && (convertType(n, ((SingleVariableDeclaration) d.parameters()
                    .get(i)).getType()) instanceof NonPrimitiveType))
            && convertType(n,
                ((SingleVariableDeclaration) d.parameters().get(i)).getType())
            != a[i])
          throw new Error(
              n, "Type mismatch the " + i + " argument in \"" + n + "\"");
      setResult(n, convertType(n, d.getReturnType2()));
    }
    @Override
    public boolean visit(BooleanLiteral n) {
      setResult(n, tf.Boolean);
      return false;
    }
    @Override
    public boolean visit(NumberLiteral n) {
      setResult(n, tf.Int);
      return false;
    }
    @Override
    public boolean visit(ParenthesizedExpression n) {
      n.getExpression().accept(this);
      setResult(n, getResult());
      return false;
    }
    @Override
    public boolean visit(FieldDeclaration n) {
      resultTypeMap.put(n, convertType(n, n.getType()));
      return false;
    }
    @Override
    public boolean visit(MethodDeclaration n) {
      methodReturnType = convertType(n, n.getReturnType2());
      List<Type> p = new ArrayList<>();
      for (Object o : n.parameters()) {
        SingleVariableDeclaration d = (SingleVariableDeclaration) o;
        Type t = convertType(n, d.getType());
        p.add(t);
        resultTypeMap.put(d, t);
      }
      resultMethodTypeMap.put(
          n, new Pair<Type, List<Type>>(methodReturnType, p));
      n.getBody().accept(this);
      methodReturnType = null;
      return false;
    }
    @Override
    public boolean visit(MethodInvocation n) {
      String c = n.getExpression() == null
          ? className
          : ((SimpleName) n.getExpression()).getIdentifier(),
             m = n.getName().getIdentifier();
      Type[] a = new Type[n.arguments().size()];
      for (int i = 0; i < a.length; i++) {
        ((Expression) n.arguments().get(i)).accept(this);
        a[i] = getResult();
      }
      Object o = symbolMap.get(n);
      if (o == null || o instanceof Method)
        typeCheckMethodInvocation(
            n, c, m, a, o == null ? resolveMethod(n, c, m, a) : (Method) o);
      else
        typeCheckMethodInvocation(n, c, m, a, (MethodDeclaration) o);
      return false;
    }
    @Override
    public boolean visit(SimpleName n) {
      ASTNode p = n.getParent();
      if (p instanceof Expression || p instanceof Statement) {
        Object o = symbolMap.get(n);
        org.eclipse.jdt.core.dom.Type t = o instanceof FieldDeclaration
            ? ((FieldDeclaration) o).getType()
            : o instanceof SingleVariableDeclaration
            ? ((SingleVariableDeclaration) o).getType()
            : o instanceof VariableDeclarationStatement
            ? ((VariableDeclarationStatement) o).getType()
            : null;
        if (t == null)
          throw new Error(n, "Unexpected SimpleName: '" + n + "'");
        setResult(n, convertType(n, t));
      }
      return false;
    }
    @Override
    public boolean visit(TypeDeclaration n) {
      className = n.getName().getIdentifier();
      resultTypeMap.put(n, tf.getClassType(className));
      return true;
    }
    @Override
    public boolean visit(VariableDeclarationStatement n) {
      resultTypeMap.put(n, convertType(n, n.getType()));
      return false;
    }
    @Override
    public boolean visit(Assignment n) {
      n.getLeftHandSide().accept(this);
      Type l = getResult();
      n.getRightHandSide().accept(this);
      Type r = getResult();
      if (!((r instanceof NullType) && (l instanceof NonPrimitiveType))
          && l != r)
        throw new Error(n, "Type mismatch in \"" + n + "\": " + l + " = " + r);
      return false;
    }
    @Override
    public boolean visit(ExpressionStatement n) {
      if (n.getExpression() instanceof PostfixExpression) {
        n.getExpression().accept(this);
        assert getResult() == null;
        return false;
      }
      n.getExpression().accept(this);
      if (!(n.getExpression() instanceof Assignment)
          && !(n.getExpression() instanceof MethodInvocation))
        throw new Error(n, "Unexpected expression statement: '" + n + "'");
      getResult();
      return false;
    }
    @Override
    public boolean visit(InfixExpression n) {
      InfixExpression.Operator o = n.getOperator();
      if (o == InfixExpression.Operator.AND
          || o == InfixExpression.Operator.OR
          || o == InfixExpression.Operator.LEFT_SHIFT
          || o == InfixExpression.Operator.RIGHT_SHIFT_SIGNED
          || o == InfixExpression.Operator.RIGHT_SHIFT_UNSIGNED
          || o == InfixExpression.Operator.XOR) {
        n.getLeftOperand().accept(this);
        Type l = getResult();
        n.getRightOperand().accept(this);
        Type r = getResult();
        if (l != tf.Int)
          throw new Error(n,
              "Expecting an int type expression as the left-hand operand of \""
                  + o + "\" in \"" + n + "\"");
        if (r != tf.Int)
          throw new Error(n,
              "Expecting an int type expression as the right-hand operand of \""
                  + o + "\" in \"" + n + "\"");
        setResult(n, tf.Int);
        return false;
      } else if (o == InfixExpression.Operator.EQUALS
          || o == InfixExpression.Operator.NOT_EQUALS) {
        n.getLeftOperand().accept(this);
        Type l = getResult();
        n.getRightOperand().accept(this);
        Type r = getResult();
        if (!((l instanceof NullType) && (r instanceof NonPrimitiveType))
            && !((r instanceof NullType) && (l instanceof NonPrimitiveType))
            && l != r)
          throw new Error(
              n, "Type mismatch in \"" + n + "\": " + l + " " + o + " " + r);
        setResult(n, tf.Boolean);
        return false;
      }
      n.getLeftOperand().accept(this);
      Type l = getResult();
      n.getRightOperand().accept(this);
      Type r = getResult();
      if (o == InfixExpression.Operator.PLUS
          || o == InfixExpression.Operator.MINUS
          || o == InfixExpression.Operator.TIMES
          || o == InfixExpression.Operator.DIVIDE
          || o == InfixExpression.Operator.REMAINDER
          || o == InfixExpression.Operator.LESS
          || o == InfixExpression.Operator.GREATER
          || o == InfixExpression.Operator.LESS_EQUALS
          || o == InfixExpression.Operator.GREATER_EQUALS) {
        if (l != tf.Int)
          throw new Error(n,
              "Expecting an int type expression as the left-hand operand of \""
                  + o + "\" in \"" + n + "\"");
        if (r != tf.Int)
          throw new Error(n,
              "Expecting an int type expression as the right-hand operand of \""
                  + o + "\" in \"" + n + "\"");
        setResult(n, (o == InfixExpression.Operator.LESS
            || o == InfixExpression.Operator.GREATER
            || o == InfixExpression.Operator.LESS_EQUALS
            || o == InfixExpression.Operator.GREATER_EQUALS)
            ? tf.Boolean : tf.Int);
        return false;
      } else if (o == InfixExpression.Operator.CONDITIONAL_AND
          || o == InfixExpression.Operator.CONDITIONAL_OR) {
        if (l != tf.Boolean)
          throw new Error(n,
              "Expecting a boolean type expression as the left-hand operand of \""
                  + o + "\" in \"" + n + "\"");
        if (r != tf.Boolean)
          throw new Error(n,
              "Expecting a boolean type expression as the right-hand operand of \""
                  + o + "\" in \"" + n + "\"");
        setResult(n, tf.Boolean);
        return false;
      }
      throw new Error(n, "Unexpected InfixExpression: '" + n + "'");
    }
    @Override
    public boolean visit(PrefixExpression n) {
      if (n.getOperator() == PrefixExpression.Operator.COMPLEMENT) {
        n.getOperand().accept(this);
        Type t = getResult();
        if (t != tf.Int)
          throw new Error(n,
              "Expecting an int type expression as the operand of \""
                  + n.getOperator() + "\" in \"" + n + "\"");
        setResult(n, tf.Int);
        return false;
      }
      n.getOperand().accept(this);
      Type t = getResult();
      if ((n.getOperator() == PrefixExpression.Operator.PLUS
              || n.getOperator() == PrefixExpression.Operator.MINUS)
          && t == tf.Int) {
        setResult(n, tf.Int);
        return false;
      }
      if (n.getOperator() == PrefixExpression.Operator.NOT && t == tf.Boolean) {
        setResult(n, tf.Boolean);
        return false;
      }
      if (n.getOperator() == PrefixExpression.Operator.NOT)
        throw new Error(n,
            "Expecting a boolean type expression as the operand of \""
                + n.getOperator() + "\" in \"" + n + "\"");
      throw new Error(n,
          "Expecting an int type expression as the operand of \""
              + n.getOperator() + "\" in \"" + n + "\"");
    }
    @Override
    public boolean visit(ReturnStatement n) {
      Expression e = n.getExpression();
      if (methodReturnType == tf.Void && e != null)
        throw new Error(n, "Unexpected return's expression in \"" + n + "\"");
      if (methodReturnType != tf.Void && e == null)
        throw new Error(n, "Expecting a return's expression in \"" + n + "\"");
      if (e != null) {
        e.accept(this);
        Type t = getResult();
        if (!((t instanceof NullType)
                && (methodReturnType instanceof NonPrimitiveType))
            && t != methodReturnType)
          throw new Error(n, "Expecting " + methodReturnType.name
              + " return expression in \"" + n + "\"");
      }
      return false;
    }
    @Override
    public boolean visit(IfStatement n) {
      n.getExpression().accept(this);
      if (getResult() != tf.Boolean)
        throw new Error(n,
            "Expecting a boolean type expression as the condition of an "
            + "if-statement: \""
                + n.getExpression() + "\"");
      n.getThenStatement().accept(this);
      if (n.getElseStatement() != null)
        n.getElseStatement().accept(this);
      return false;
    }
    @Override
    public boolean visit(WhileStatement n) {
      n.getExpression().accept(this);
      if (getResult() != tf.Boolean)
        throw new Error(n,
            "Expecting a boolean type expression as the condition of a "
            + "while-statement: \""
                + n.getExpression() + "\"");
      n.getBody().accept(this);
      return false;
    }

    @Override
    public boolean visit(final ArrayAccess node) {
      node.getArray().accept(this);
      Type t = getResult();
      if (!(t instanceof ArrayType)) {
        throw new Error(node, "Expecting an array type in \'" + node + "\"");
      }
      final ArrayType at = (ArrayType) t;
      node.getIndex().accept(this);
      t = getResult();
      if (t != this.tf.Int) {
        throw new Error(node, "Expecting an int type for the index of \'"
            + node + "\"");
      }
      setResult(node, at.baseType);
      return false;
    }

    @Override
    public boolean visit(final ArrayCreation node) {
      final ArrayType at = (ArrayType) convertType(node, node.getType());
      final ArrayInitializer a = node.getInitializer();
      if (a == null) {
        ((ASTNode) node.dimensions().get(0)).accept(this);
        final Type t = getResult();
        if (t != this.tf.Int) {
          throw new Error(node,
              "Expecting an int type expression for the array length in \'"
                  + node + "\'");
        }
      } else {
        final BaseType bt = at.baseType;
        int i = 0;
        for (final Object o : a.expressions()) {
          ((ASTNode) o).accept(this);
          final Type t = getResult();
          if ((t instanceof NullType) && (bt instanceof NonPrimitiveType)) {
            // ok
          } else if (t != bt) {
            throw new Error(node, "Type mismatch in array initializer element "
                + i + " in \'" + node + "\': " + bt + " : " + t);
          }
          i++;
        }
      }
      setResult(node, at);
      return false;
    }

    @Override
    public boolean visit(final ClassInstanceCreation node) {
      final String className = ((SimpleType) node.getType()).getName()
          .getFullyQualifiedName();
      if (!this.classMap.containsKey(className)) {
        throw new Error(node, "Undeclared class type in \'" + node + "\"");
      }
      setResult(node, this.tf.getClassType(className));
      this.symbolMap.put(node, this.classMap.get(className));
      return false;
    }

    @Override
    public boolean visit(final ConditionalExpression node) {
      node.getExpression().accept(this);
      final Type eType = getResult();
      if (eType != this.tf.Boolean) {
        throw new Error(node,
            "Expecting a boolean type expression as the condition in \'" + node
                + "\"");
      }
      node.getThenExpression().accept(this);
      final Type thenType = getResult();
      node.getElseExpression().accept(this);
      final Type elseType = getResult();
      if ((thenType instanceof NullType)
          && (elseType instanceof NonPrimitiveType)) {
        setResult(node, elseType);
        return false;
      } else if ((thenType instanceof NonPrimitiveType)
          && (elseType instanceof NullType)) {
        setResult(node, thenType);
        return false;
      } else if (thenType != elseType) {
        throw new Error(node, "Type mismatch for conditional expression \'"
            + node + "\": " + thenType + " : " + elseType);
      }
      setResult(node, thenType);
      return false;
    }

    @Override
    public boolean visit(final DoStatement node) {
      node.getBody().accept(this);
      node.getExpression().accept(this);
      final Type t = getResult();
      if (t != this.tf.Boolean) {
        throw new Error(node,
            "Expecting a boolean type expression as the condition of a while-statement: \""
                + node.getExpression() + "\"");
      }
      return false;
    }

    @Override
    public boolean visit(final FieldAccess node) {
      node.getExpression().accept(this);
      final Type t = getResult();
      if (!(t instanceof ClassType)) {
        throw new Error(node,
            "Expecting a class type expression in field access: \'" + node
                + "\'");
      }
      final ClassType ct = (ClassType) t;
      final String fieldName = node.getName().getIdentifier();
      final Type fieldType = ct.fieldTypeMap.get(fieldName);
      if (fieldType == null) {
        throw new Error(node,
            "Undeclared field in field access of class type \'" + ct + "\': \'"
                + node + "\'");
      }
      setResult(node, fieldType);
      this.symbolMap.put(node, this.fieldMap.get(new Pair<String, String>(
          ct.name, fieldName)));
      return false;
    }

    @Override
    public boolean visit(final ForStatement node) {
      for (final Object o : node.initializers()) {
        ((ASTNode) o).accept(this);
      }
      final Expression e = node.getExpression();
      if (e != null) {
        e.accept(this);
        final Type t = getResult();
        if (t != this.tf.Boolean) {
          throw new Error(node,
              "Expecting a boolean type expression as the condition of a for-statement: \""
                  + node.getExpression() + "\"");
        }
      }
      for (final Object o : node.updaters()) {
        ((ASTNode) o).accept(this);
      }
      node.getBody().accept(this);
      return false;
    }

    @Override
    public boolean visit(final NullLiteral node) {
      setResult(node, this.tf.Null);
      return false;
    }

    @Override
    public boolean visit(final PostfixExpression node) {
      node.getOperand().accept(this);
      final Type t = getResult();
      if (t != this.tf.Int) {
        throw new Error(node,
            "Expecting an int type expression as the operand of \'"
                + node.getOperator() + "\': \"" + node.getOperand() + "\"");
      }
      // no need to set the type result for postfix expressions since
      // postfix expressions in StaticJava are statements,
      // i.e., they are evaluated for their side-effects.
      return false;
    }

  }

  /**
   * Type checks an ExtendedStaticJava {@link CompilationUnit} with the given
   * {@link SymbolTable} and the given {@link TypeFactory}. It also
   * resolves {@link MethodInvocation} of library call (and put its mapping in
   * the {@link SymbolTable}).
   * 
   * @param tf
   *          The {@link TypeFactory}.
   * @param cu
   *          The StaticJava {@link CompilationUnit}.
   * @param symbolTable
   *          The {@link SymbolTable} of the {@link CompilationUnit}
   * @return The {@link TypeTable}.
   * @throws Error
   *           If the type checker encounter type error in the
   *           {@link CompilationUnit}.
   */
  public static @NonNull
  TypeTable check(@NonNull final TypeFactory tf,
      @NonNull final CompilationUnit cu,
      @NonNull final SymbolTable symbolTable) throws Error {
    assert (tf != null) && (cu != null) && (symbolTable != null);

    final Visitor v = new Visitor(tf, symbolTable);
    cu.accept(v);
    final TypeTable result = new TypeTable(v.resultTypeMap,
        v.resultMethodTypeMap);
    v.dispose();
    return result;
  }

  /**
   * Declared as protected to disallow creation of this object outside from the
   * methods of this class.
   */
  protected TypeChecker() {
  }
}
