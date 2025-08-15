boolean isAssignedOnceInLifetime() {
  Reference ref = getOneAndOnlyAssignment();
  if (ref == null) {
    return false;
  }

  // Make sure this assignment is not in a loop.
  for (BasicBlock block = ref.getBasicBlock();
       block != null; block = block.getParent()) {
    if (block.isFunction()) { // Corrected to call isFunction() as a method
      break;
    } else if (block.isLoop()) { // Corrected to call isLoop() as a method
      return false;
    }
  }

  return true;
}
