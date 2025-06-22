boolean isAssignedOnceInLifetime() {
  Reference ref = getOneAndOnlyAssignment();
  if (ref == null) {
    return false;
  }

  ReferenceCollection refs = getReferences(ref.getSymbol());
  if (refs != null && refs.getReferences().contains(ref)) {
    for (Reference r : refs.getReferences()) {
      // If this assignment is referenced from another assignment or used inside a loop, return false.
      if (r.getBasicBlock().isLoop) {
        return false;
      }
    }
  }

  // Make sure this assignment is not in a loop.
  for (BasicBlock block = ref.getBasicBlock();
       block != null; block = block.getParent()) {
    if (block.isFunction) {
      break;
    } else if (block.isLoop) {
      return false;
    }
  }

  return true;
}
