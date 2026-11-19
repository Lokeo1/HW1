/* Bridge fixed-width LLVM IR arguments to the target C library's size_t. */
#include <stdint.h>
#include <stdlib.h>

void *sj_calloc(uint64_t count, uint64_t size) {
  if (count > SIZE_MAX || size > SIZE_MAX) {
    return NULL;
  }
  return calloc((size_t)count, (size_t)size);
}
