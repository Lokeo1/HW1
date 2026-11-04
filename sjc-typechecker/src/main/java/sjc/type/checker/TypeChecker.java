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
      // TODO
      return false;
    }
    @Override
    public boolean visit(FieldDeclaration n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(MethodDeclaration n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(MethodInvocation n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(SimpleName n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(TypeDeclaration n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(VariableDeclarationStatement n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(Assignment n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(ExpressionStatement n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(InfixExpression n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(PrefixExpression n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(ReturnStatement n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(IfStatement n) {
      // TODO
      return false;
    }
    @Override
    public boolean visit(WhileStatement n) {
      // TODO
      return false;
    }

    @Override
    public boolean visit(final ArrayAccess node) {
      // TODO
      return false;
    }

    @Override
    public boolean visit(final ArrayCreation node) {
      // TODO
      return false;
    }

    @Override
    public boolean visit(final ClassInstanceCreation node) {
      // TODO
      return false;
    }

    @Override
    public boolean visit(final ConditionalExpression node) {
      // TODO
      return false;
    }

    @Override
    public boolean visit(final DoStatement node) {
      // TODO
      return false;
    }

    @Override
    public boolean visit(final FieldAccess node) {
      // TODO
      return false;
    }

    @Override
    public boolean visit(final ForStatement node) {
      // TODO
      return false;
    }

    @Override
    public boolean visit(final NullLiteral node) {
      // TODO
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
