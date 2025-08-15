boolean isAssignedOnceInLifetime() {
  Reference ref = getOneAndOnlyAssignment();
  if (ref == null) {
    return false;
  }

  ReferenceCollection refs = getReferences(ref.getSymbol());
  if (refs == null) {
    return false;
  }

  // Make sure this assignment is not in a loop by checking all references.
  for (Reference reference : refs.references()) {
    BasicBlock block = reference.getBasicBlock();
    while (block != null) {
      if (block.isFunction) {
        break;
      } else if (block.isLoop) {
        return false;
      }
      block = block.getParent();
    }
  }

  return true;
}
