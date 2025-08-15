boolean isAssignedOnceInLifetime() {
  Reference ref = getOneAndOnlyAssignment();
  if (ref == null) {
    return false;
  }

  BasicBlock assignmentBlock = ref.getBasicBlock();

  // Make sure this assignment is not in a loop.
  for (BasicBlock block = assignmentBlock;
       block != null; block = block.getParent()) {
    if (block.isFunction) {
      break;
    } else if (block.isLoop) {
      return false;
    }
  }

  return true;
}
