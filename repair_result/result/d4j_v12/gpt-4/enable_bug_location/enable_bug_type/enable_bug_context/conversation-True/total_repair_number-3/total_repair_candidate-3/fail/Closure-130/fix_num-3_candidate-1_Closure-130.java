private void inlineAliases(GlobalNamespace namespace) {
  // Invariant: All the names in the worklist meet condition (a).
  Deque<Name> workList = new ArrayDeque<Name>(namespace.getNameForest());
  while (!workList.isEmpty()) {
    Name name = workList.pop();

    // Don't attempt to inline a getter or setter property as a variable.
    if (name.type == Name.Type.GET || name.type == Name.Type.SET) {
      continue;
    }

    if (name.globalSets == 1 && name.localSets == 0 &&
        name.aliasingGets > 0) {
      // {@code name} meets condition (b). Find all of its local aliases
      // and try to inline them.
      List<Ref> refs = Lists.newArrayList(name.getRefs());
      for (Ref ref : refs) {
        if (ref.type == Type.ALIASING_GET && ref.scope.isLocal()) {
          // {@code ref} meets condition (c). Try to inline it.
          if (inlineAliasIfPossible(ref, namespace)) {
            name.removeRef(ref);
          }
        }
      }
    } else if (name.type == Name.Type.OBJECTLIT ||
               name.type == Name.Type.FUNCTION) {
      // Only add children to the worklist if there are any aliasingGets,
      // or the name has not been fully inlined.
      if (name.aliasingGets > 0 || name.props != null) {
        workList.addAll(name.props != null ? name.props : Collections.emptyList());
      }
    }
  }
}
