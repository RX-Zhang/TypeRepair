boolean isAssignedOnceInLifetime() {
  Reference ref = getOneAndOnlyAssignment();
  if (ref == null) {
    return false;
  }

  BasicBlock assignedBlock = ref.getBasicBlock();
  
  // Traverse up the block stack from the innermost block to the function block,
  // checking if the assignment is inside a loop.
  for (BasicBlock block = assignedBlock; block != null; block = block.getParent()) {
    if (block.isFunction) {
      break;
    } else if (block.isLoop) {
      return false;
    }
  }

  return true;
}
