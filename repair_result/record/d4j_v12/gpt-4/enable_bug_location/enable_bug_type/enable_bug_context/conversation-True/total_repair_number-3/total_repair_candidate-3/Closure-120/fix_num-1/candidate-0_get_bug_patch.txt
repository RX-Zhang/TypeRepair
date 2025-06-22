boolean isAssignedOnceInLifetime() {
  Reference ref = getOneAndOnlyAssignment();
  if (ref == null) {
    return false;
  }

  Scope scope = getScope(ref.getVariable());
  BasicBlock lastBlock = null;

  for (BasicBlock block = ref.getBasicBlock();
       block != null && block != lastBlock; block = block.getParent()) {
    lastBlock = block;
    if (block.isFunction) {
      break;
    } else if (block.isLoop) {
      return false;
    }
  }

  return true;
}
