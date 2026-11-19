package sjc.llgen;

import sjc.annotation.NonNull;

/** The textual LLVM IR produced for one Extended Static Java program. */
public final class LLVMIRModule implements CharSequence {
  public final @NonNull String moduleName;
  public final @NonNull String mainClassName;
  public final @NonNull String llvmIR;

  LLVMIRModule(@NonNull final String moduleName,
      @NonNull final String mainClassName, @NonNull final String llvmIR) {
    if ((moduleName == null) || (mainClassName == null) || (llvmIR == null)) {
      throw new IllegalArgumentException("LLVM module values must be non-null");
    }
    this.moduleName = moduleName;
    this.mainClassName = mainClassName;
    this.llvmIR = llvmIR;
  }

  @Override
  public int length() {
    return this.llvmIR.length();
  }

  @Override
  public char charAt(final int index) {
    return this.llvmIR.charAt(index);
  }

  @Override
  public CharSequence subSequence(final int start, final int end) {
    return this.llvmIR.subSequence(start, end);
  }

  @Override
  public String toString() {
    return this.llvmIR;
  }
}
