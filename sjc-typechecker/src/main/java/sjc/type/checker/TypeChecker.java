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
      Type previousReturnType = methodReturnType;
      methodReturnType = convertType(n, n.getReturnType2());
      List<Type> parameterTypes = new ArrayList<>(n.parameters().size());
      for (Object parameter : n.parameters()) {
        SingleVariableDeclaration declaration =
            (SingleVariableDeclaration) parameter;
        Type type = convertType(declaration, declaration.getType());
        resultTypeMap.put(declaration, type);
        parameterTypes.add(type);
      }
      resultMethodTypeMap.put(n,
          new Pair<Type, List<Type>>(methodReturnType, parameterTypes));
      try {
        if (n.getBody() != null)
          n.getBody().accept(this);
      } finally {
        methodReturnType = previousReturnType;
      }
      return false;
    }
    @Override
    public boolean visit(MethodInvocation n) {
      List<?> arguments = n.arguments();
      Type[] argumentTypes = new Type[arguments.size()];
      for (int i = 0; i < argumentTypes.length; i++) {
        ((Expression) arguments.get(i)).accept(this);
        argumentTypes[i] = getResult();
      }
      String methodName = n.getName().getIdentifier();
      String declaringClass = n.getExpression() == null
          ? className : n.getExpression().toString();
      Object declaration = symbolMap.get(n);
      if (declaration instanceof MethodDeclaration) {
        typeCheckMethodInvocation(n, declaringClass, methodName, argumentTypes,
            (MethodDeclaration) declaration);
      } else {
        Method method = declaration instanceof Method
            ? (Method) declaration
            : resolveMethod(n, declaringClass, methodName, argumentTypes);
        typeCheckMethodInvocation(n, declaringClass, methodName, argumentTypes,
            method);
      }
      return false;
    }
    @Override
    public boolean visit(SimpleName n) {
      Object declaration = symbolMap.get(n);
      Type type;
      if (declaration instanceof VariableDeclarationStatement) {
        type = convertType(n,
            ((VariableDeclarationStatement) declaration).getType());
      } else if (declaration instanceof SingleVariableDeclaration) {
        type = convertType(n,
            ((SingleVariableDeclaration) declaration).getType());
      } else if (declaration instanceof FieldDeclaration) {
        type = convertType(n, ((FieldDeclaration) declaration).getType());
      } else {
        throw new Error(n, "Unresolvable variable: '" + n + "'");
      }
      setResult(n, type);
      return false;
    }
    @Override
    public boolean visit(TypeDeclaration n) {
      String previousClassName = className;
      className = n.getName().getIdentifier();
      resultTypeMap.put(n, tf.getClassType(className));
      try {
        for (Object declaration : n.bodyDeclarations())
          ((ASTNode) declaration).accept(this);
      } finally {
        className = previousClassName;
      }
      return false;
    }
    @Override
    public boolean visit(VariableDeclarationStatement n) {
      Type type = convertType(n, n.getType());
      resultTypeMap.put(n, type);
      for (Object fragment : n.fragments()) {
        Expression initializer =
            ((VariableDeclarationFragment) fragment).getInitializer();
        if (initializer != null) {
          initializer.accept(this);
          Type actual = getResult();
          if (type == null || actual == null || type == tf.Void
              || (type != actual && !(actual instanceof NullType
                  && type instanceof NonPrimitiveType)))
            throw new Error(n, "Type mismatch in \"" + n + "\": "
                + type + " = " + actual);
        }
      }
      return false;
    }
    @Override
    public boolean visit(Assignment n) {
      n.getLeftHandSide().accept(this);
      Type left = getResult();
      n.getRightHandSide().accept(this);
      Type right = getResult();
      if (left == null || right == null || left == tf.Void
          || (left != right && !(right instanceof NullType
              && left instanceof NonPrimitiveType)))
        throw new Error(n, "Type mismatch in \"" + n + "\": "
            + left + " " + n.getOperator() + " " + right);
      return false;
    }
    @Override
    public boolean visit(ExpressionStatement n) {
      n.getExpression().accept(this);
      getResult();
      return false;
    }
    @Override
    public boolean visit(InfixExpression n) {
      n.getLeftOperand().accept(this);
      Type left = getResult();
      InfixExpression.Operator op = n.getOperator();
      List<?> extendedOperands = n.extendedOperands();
      for (int i = -1; i < extendedOperands.size(); i++) {
        Expression operand = i < 0 ? n.getRightOperand()
            : (Expression) extendedOperands.get(i);
        operand.accept(this);
        Type right = getResult();
        if (op == InfixExpression.Operator.EQUALS
            || op == InfixExpression.Operator.NOT_EQUALS) {
          if (left == null || right == null || left == tf.Void || right == tf.Void
              || (left != right
                  && !(right instanceof NullType && left instanceof NonPrimitiveType)
                  && !(left instanceof NullType && right instanceof NonPrimitiveType)))
            throw new Error(n, "Type mismatch in \"" + n + "\": "
                + left + " " + op + " " + right);
          left = tf.Boolean;
        } else {
          boolean logical = op == InfixExpression.Operator.CONDITIONAL_AND
              || op == InfixExpression.Operator.CONDITIONAL_OR;
          boolean bitwise = op == InfixExpression.Operator.AND
              || op == InfixExpression.Operator.OR || op == InfixExpression.Operator.XOR;
          Type expected = logical || (bitwise && left == tf.Boolean)
              ? tf.Boolean : tf.Int;
          if (left != expected || right != expected)
            throw new Error(n, "Expecting "
                + (expected == tf.Int ? "an int" : "a boolean")
                + " type expression as the " + (left != expected ? "left" : "right")
                + "-hand operand of \"" + op + "\" in \"" + n + "\"");
          if (op == InfixExpression.Operator.LESS
              || op == InfixExpression.Operator.LESS_EQUALS
              || op == InfixExpression.Operator.GREATER
              || op == InfixExpression.Operator.GREATER_EQUALS)
            left = tf.Boolean;
          else
            left = expected;
        }
      }
      setResult(n, left);
      return false;
    }
    @Override
    public boolean visit(PrefixExpression n) {
      n.getOperand().accept(this);
      Type expected = n.getOperator() == PrefixExpression.Operator.NOT
          ? tf.Boolean : tf.Int;
      if (getResult() != expected)
        throw new Error(n, "Expecting "
            + (expected == tf.Int ? "an int" : "a boolean")
            + " type expression as the operand of \"" + n.getOperator()
            + "\" in \"" + n + "\"");
      setResult(n, expected);
      return false;
    }
    @Override
    public boolean visit(ReturnStatement n) {
      if (methodReturnType == tf.Void) {
        if (n.getExpression() != null)
          throw new Error(n, "Unexpected return's expression in \"" + n + "\"");
      } else {
        Type actual = null;
        if (n.getExpression() != null) {
          n.getExpression().accept(this);
          actual = getResult();
        }
        if (methodReturnType == null || actual == null
            || (methodReturnType != actual && !(actual instanceof NullType
                && methodReturnType instanceof NonPrimitiveType)))
          throw new Error(n, "Expecting " + methodReturnType
              + " return expression in \"" + n + "\"");
      }
      return false;
    }
    @Override
    public boolean visit(IfStatement n) {
      n.getExpression().accept(this);
      if (getResult() != tf.Boolean)
        throw new Error(n.getExpression(),
            "Expecting a boolean type expression as the condition of a "
                + "if-statement: \"" + n.getExpression() + "\"");
      n.getThenStatement().accept(this);
      if (n.getElseStatement() != null)
        n.getElseStatement().accept(this);
      return false;
    }
    @Override
    public boolean visit(WhileStatement n) {
      n.getExpression().accept(this);
      if (getResult() != tf.Boolean)
        throw new Error(n.getExpression(),
            "Expecting a boolean type expression as the condition of a "
                + "while-statement: \"" + n.getExpression() + "\"");
      n.getBody().accept(this);
      return false;
    }

    @Override
    public boolean visit(final ArrayAccess node) {
      node.getArray().accept(this);
      Type array = getResult();
      if (!(array instanceof ArrayType))
        throw new Error(node, "Expecting an array type in '" + node + "'");
      node.getIndex().accept(this);
      if (getResult() != tf.Int)
        throw new Error(node, "Expecting an int type for the index of '"
            + node + "\"");
      setResult(node, ((ArrayType) array).baseType);
      return false;
    }

    @Override
    public boolean visit(final ArrayCreation node) {
      ArrayType type = (ArrayType) convertType(node, node.getType());
      for (Object dimension : node.dimensions()) {
        ((Expression) dimension).accept(this);
        if (getResult() != tf.Int)
          throw new Error(node,
              "Expecting an int type expression for the array length in '"
                  + node + "'");
      }
      ArrayInitializer initializer = node.getInitializer();
      if (initializer != null) {
        List<?> elements = initializer.expressions();
        for (int i = 0; i < elements.size(); i++) {
          ((Expression) elements.get(i)).accept(this);
          Type actual = getResult();
          if (actual == null || (type.baseType != actual
              && !(actual instanceof NullType
                  && type.baseType instanceof NonPrimitiveType)))
            throw new Error(node, "Type mismatch in array initializer element "
                + i + " in '" + node + "': " + type.baseType + " : " + actual);
        }
      }
      setResult(node, type);
      return false;
    }

    @Override
    public boolean visit(final ClassInstanceCreation node) {
      setResult(node, convertType(node, node.getType()));
      return false;
    }

    @Override
    public boolean visit(final ConditionalExpression node) {
      node.getExpression().accept(this);
      if (getResult() != tf.Boolean)
        throw new Error(node,
            "Expecting a boolean type expression as the condition in '"
                + node + "\"");
      node.getThenExpression().accept(this);
      Type thenType = getResult();
      node.getElseExpression().accept(this);
      Type elseType = getResult();
      if (thenType == null || elseType == null
          || thenType == tf.Void || elseType == tf.Void
          || (thenType != elseType
              && !(elseType instanceof NullType && thenType instanceof NonPrimitiveType)
              && !(thenType instanceof NullType && elseType instanceof NonPrimitiveType)))
        throw new Error(node, "Type mismatch for conditional expression '"
            + node + "\": " + thenType + " : " + elseType);
      setResult(node, thenType instanceof NullType ? elseType : thenType);
      return false;
    }

    @Override
    public boolean visit(final DoStatement node) {
      node.getBody().accept(this);
      node.getExpression().accept(this);
      if (getResult() != tf.Boolean)
        throw new Error(node.getExpression(),
            "Expecting a boolean type expression as the condition of a "
                + "while-statement: \"" + node.getExpression() + "\"");
      return false;
    }

    @Override
    public boolean visit(final FieldAccess node) {
      node.getExpression().accept(this);
      Type receiver = getResult();
      String name = node.getName().getIdentifier();
      Type type = null;
      if (receiver instanceof ArrayType && "length".equals(name))
        type = tf.Int;
      else if (receiver instanceof ClassType)
        type = ((ClassType) receiver).fieldTypeMap.get(name);
      if (type == null)
        throw new Error(node, "Unresolvable field: '" + node + "'");
      setResult(node, type);
      return false;
    }

    @Override
    public boolean visit(final ForStatement node) {
      for (Object initializer : node.initializers()) {
        ((Expression) initializer).accept(this);
        getResult();
      }
      if (node.getExpression() != null) {
        node.getExpression().accept(this);
        if (getResult() != tf.Boolean)
          throw new Error(node.getExpression(),
              "Expecting a boolean type expression as the condition of a "
                  + "for-statement: \"" + node.getExpression() + "\"");
      }
      for (Object updater : node.updaters()) {
        ((Expression) updater).accept(this);
        getResult();
      }
      node.getBody().accept(this);
      return false;
    }

    @Override
    public boolean visit(final NullLiteral node) {
      setResult(node, tf.Null);
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
