package sjc.type;

import sjc.annotation.NonNull;

/**
 * This class represents a base type of an {@link ArrayType} in StaticJava.
 * 
 */
public abstract class BaseType extends Type {
  /**
   * Constucts a base type with the given name.
   * 
   * @param name
   *          The name of this base type.
   */
  protected BaseType(@NonNull final String name) {
    super(name);
  }
}
