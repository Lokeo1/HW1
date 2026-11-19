package sjc.symboltable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;

import sjc.annotation.NonNull;
import sjc.annotation.NonNullElements;
import sjc.annotation.ReadOnly;
import sjc.util.Pair;

/**
 * This class is used to build symbol table for an ExtendedStaticJava
 * {@link CompilationUnit}. Note that the algorithm assumes that the JDT AST
 * tree was built using the {@link esjc.ast.ExtendedStaticJavaASTBuilder}. That is, it
 * assumes certain structures on the AST, e.g., a class does not have an
 * instance method.
 *
 */
public class SymbolTableBuilder {
  /** Signals an error while building an extended symbol table. */
  public static class Error extends RuntimeException {
    private static final long serialVersionUID = 7425398360365890486L;

    public final @NonNull
    @ReadOnly
    ASTNode node;

    public Error(@NonNull final ASTNode node, @NonNull final String msg) {
      super(msg);
      assert (msg != null) && (node != null);
      this.node = node;
    }
  }

  /**
   * The visitor for {@link ASTNode} to resolve symbols.
   *
   */
  private static class Visitor extends ASTVisitor {
    public @NonNull
    Map<ASTNode, Object> result = new HashMap<>();

    protected @NonNull
    Map<String, ASTNode> nameMap = new HashMap<>();

    protected @NonNull
    Map<String, MethodDeclaration> methodMap = new HashMap<>();

    protected @NonNull
    Set<String> localNames = new HashSet<>();

    protected String className;

    private @NonNullElements
    Map<String, TypeDeclaration> classMap = new HashMap<>();

    private @NonNullElements
    Map<Pair<String, String>, FieldDeclaration> fieldMap = new HashMap<>();

    /**
     * Unlink references and clear maps/collections.
     */
    protected void dispose() {
      this.result = null;
      if (this.nameMap != null) {
        this.nameMap.clear();
        this.nameMap = null;
      }
      if (this.methodMap != null) {
        this.methodMap.clear();
        this.methodMap = null;
      }
      this.className = null;
    }

    /**
     * Determines whether a {@link List} of {@link Modifier}s has a public
     * modifier.
     * 
     * @param modifiers
     *        the list of modifiers
     *
     * @return True, if the {@link List} contains a public modifier.
     */
    private boolean hasPublicModifier(final List modifiers) {
      for (final Object o : modifiers) {
        if ((o instanceof Modifier)
            && (((Modifier) o).getKeyword() == ModifierKeyword.PUBLIC_KEYWORD)) {
          return true;
        }
      }
      return false;
    }

    @Override
    public boolean visit(final FieldDeclaration node) {
      final VariableDeclarationFragment vdf = (VariableDeclarationFragment) node
          .fragments().get(0);
      final String name = vdf.getName().getIdentifier();
      if (this.nameMap.containsKey(name)) {
        throw new Error(node, "Error in field declaration '" + name
            + "' : the field name has been used in:\n" + this.nameMap.get(name));
      }
      this.nameMap.put(name, node);
      return false;
    }

    @Override
    public boolean visit(final MethodDeclaration node) {
      for (final Object o : node.parameters()) {
        final SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
        final String name = svd.getName().getIdentifier();
        if (this.nameMap.containsKey(name)) {
          throw new Error(node, "Error in method parameter declaration of '"
              + node.getName().getIdentifier() + "' : variable name '" + name
              + "' has been used in:\n" + this.nameMap.get(name));
        }

        this.localNames.add(name);
        this.nameMap.put(name, svd);
      }
      node.getBody().accept(this);

      for (final String name : this.localNames) {
        this.nameMap.remove(name);
      }
      this.localNames.clear();
      return false;
    }

    @Override
    public boolean visit(final MethodInvocation node) {
      final String methodName = node.getName().getIdentifier();

      if ((node.getExpression() == null)
          || this.className.equals(node.getExpression().toString())) {
        if (this.methodMap.containsKey(methodName)) {
          this.result.put(node, this.methodMap.get(methodName));
        } else {
          throw new Error(node, "Cannot resolve method '" + methodName
              + "' in\n:" + node);
        }
      }
      for (final Object e : node.arguments()) {
        ((Expression) e).accept(this);
      }
      return false;
    }

    @Override
    public boolean visit(final SimpleName node) {
      final String varName = node.getIdentifier();
      final ASTNode parent = node.getParent();
      if ((parent instanceof Expression) || (parent instanceof Statement)) {
        if (this.nameMap.containsKey(varName)) {
          this.result.put(node, this.nameMap.get(varName));
        } else {
          throw new Error(node, "Cannot resolve symbol '" + varName + "' in:\n"
              + parent);
        }
      }
      return false;
    }

    @Override
    public boolean visit(final VariableDeclarationStatement node) {
      final VariableDeclarationFragment vdf = (VariableDeclarationFragment) node
          .fragments().get(0);
      final String name = vdf.getName().getIdentifier();
      if (this.nameMap.containsKey(name)) {
        throw new Error(node, "Error in local variable declaration '" + name
            + "' : the variable name has been used in:\n" + this.nameMap.get(name));
      }
      this.localNames.add(name);
      this.nameMap.put(name, node);
      return false;
    }

    @Override
    public boolean visit(final ArrayCreation node) {
      // Note that we don't visit the ArrayCreation's type
      // because we want visit(SimpleName) to resolve variable references
      // instead of method names.
      for (final Object o : node.dimensions()) {
        ((ASTNode) o).accept(this);
      }
      if (node.getInitializer() != null) {
        node.getInitializer().accept(this);
      }
      return false;
    }

    @Override
    public boolean visit(final ClassInstanceCreation node) {
      // Note that we don't visit the ClassInstanceCreation's name
      // because we want visit(SimpleName) to resolve variable references
      // instead of method names.
      return false;
    }

    @Override
    public boolean visit(final FieldAccess node) {
      // Note that we don't visit the FieldAccess's name
      // because we want visit(SimpleName) to resolve variable references
      // instead of method names.
      node.getExpression().accept(this);
      return false;
    }

    @Override
    public boolean visit(final TypeDeclaration node) {
      if (hasPublicModifier(node.modifiers())) {
        // This is the main class.  Resolve its fields and methods first so
        // references in method bodies can be bound below.
        this.className = node.getName().getIdentifier();
        if ("java.lang.String".equals(this.className)) {
          throw new Error(node, "Cannot redeclare the String class");
        }
        for (final Object o : node.bodyDeclarations()) {
          if (o instanceof FieldDeclaration) {
            ((ASTNode) o).accept(this);
          } else if (o instanceof MethodDeclaration) {
            final MethodDeclaration md = (MethodDeclaration) o;
            final String methodName = md.getName().getIdentifier();
            if (this.methodMap.containsKey(methodName)) {
              throw new Error(md, "Error in method declaration '" + methodName
                  + "' : the method name has been used in:\n"
                  + this.methodMap.get(methodName));
            }
            this.methodMap.put(methodName, md);
          } else {
            throw new Error((ASTNode) o,
                "Unexpected TypeDeclaration's member: '" + node + "'");
          }
        }
        for (final Object o : node.bodyDeclarations()) {
          if (o instanceof MethodDeclaration) {
            ((ASTNode) o).accept(this);
          }
        }
        return false;
      }

      final String className = node.getName().getIdentifier();
      if (this.classMap.containsKey(className)) {
        throw new Error(node, "Error in class declaration '" + className
            + "' : the class name has been used in:\n"
            + this.classMap.get(className));
      }
      this.classMap.put(className, node);

      for (final Object o : node.bodyDeclarations()) {
        // simple class declaration only has field declarations
        final FieldDeclaration fd = (FieldDeclaration) o;

        final VariableDeclarationFragment vdf = (VariableDeclarationFragment) fd
            .fragments().get(0);
        final String name = vdf.getName().getIdentifier();
        final Pair<String, String> p = new Pair<>(className, name);
        if (this.fieldMap.containsKey(p)) {
          throw new Error(fd, "Error in field declaration '" + name
              + "' : the field name has been used in:\n"
              + this.fieldMap.get(new Pair<>(className, name)));
        }
        this.fieldMap.put(p, fd);
      }
      return false;
    }
  }

  /**
   * Builds a {@link SymbolTable} for the given ExtendedStaticJava
   * {@link CompilationUnit}.
   * 
   * @param cu
   *          The {@link CompilationUnit}.
   * @return The {@link SymbolTable} for the given
   *         {@link CompilationUnit}.
   * @throws Error
   *           If the builder encounter unresolvable symbol.
   */
  public static @NonNull
  SymbolTable build(@NonNull final CompilationUnit cu)
      throws Error {
    assert cu != null;

    final Visitor v = new Visitor();
    cu.accept(v);
    final SymbolTable result = new SymbolTable(v.result,
        v.classMap, v.fieldMap);
    v.dispose();
    return result;
  }

  /**
   * Declared as protected to disallow creation of this object outside from the
   * methods of this class.
   */
  protected SymbolTableBuilder() {
  }
}
