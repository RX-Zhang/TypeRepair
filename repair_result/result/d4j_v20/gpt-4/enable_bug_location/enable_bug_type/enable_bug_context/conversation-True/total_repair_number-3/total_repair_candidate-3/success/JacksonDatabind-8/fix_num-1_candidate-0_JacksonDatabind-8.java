protected void verifyNonDup(AnnotatedWithParams newOne, int typeIndex, boolean explicit)
{
    final int mask = (1 << typeIndex);
    _hasNonDefaultCreator = true;
    AnnotatedWithParams oldOne = _creators[typeIndex];
    // already had an explicitly marked one?
    if (oldOne != null) {

        if ((_explicitCreators & mask) != 0) { // already had explicitly annotated, leave as-is
            // but skip, if new one not annotated
            if (!explicit) {
                return;
            }
            // both explicit: verify conflict
            if (oldOne.getClass() == newOne.getClass()) {
                throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                        +" creators: already had explicitly marked "+oldOne+", encountered "+newOne);
            }
            // otherwise, allow override
        } else {
            // old one was not explicit, new one is explicit: allow override
            if (explicit) {
                // override old one below
            } else {
                // both not explicit: skip new one to keep first
                return;
            }
        }
    }
    if (explicit) {
        _explicitCreators |= mask;
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
