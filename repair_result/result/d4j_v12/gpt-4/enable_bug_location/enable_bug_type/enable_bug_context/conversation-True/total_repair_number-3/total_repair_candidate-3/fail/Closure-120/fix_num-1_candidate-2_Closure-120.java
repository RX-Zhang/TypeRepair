boolean isAssignedOnceInLifetime() {
  Reference ref = getOneAndOnlyAssignment();
  if (ref == null) {
    return false;
  }

  // Make sure this assignment is not in a loop.
  for (BasicBlock block = ref.getBasicBlock();
       block != null; block = block.getParent()) {
    if (block.isFunction) {
      break;
    } else if (block.isLoop) {
      // Check if this assignment happens multiple times by looking up references
      ReferenceCollection references = getReferences(ref.getVar());
      if (references != null && references.size() > 1) {
        return false;
      }
    }
  }

  return true;
}
