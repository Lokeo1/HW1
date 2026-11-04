package sjc.type;

/**
 * This class represents the null type in StaticJava.
 */
public class NullType extends NonBaseType
    implements NonPrimitiveType {
  /**
   * Constructs a null type.
   */
  protected NullType() {
    super("null");
  }
}
