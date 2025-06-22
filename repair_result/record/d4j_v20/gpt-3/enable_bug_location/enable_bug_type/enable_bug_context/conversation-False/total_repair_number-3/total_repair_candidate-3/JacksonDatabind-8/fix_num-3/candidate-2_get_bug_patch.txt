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
            // otherwise only verify if neither explicitly annotated.
        } else if (!explicit) {
            // old not explicit, new not explicit: fine to override
        } else {
            // old not explicit, new explicit: allow replacing non-explicit with explicit
            // continue to next check
        }

        // one more thing: ok to override in sub-class
        if (oldOne.getClass() == newOne.getClass()) {
            // same concrete class: conflicting explicit creators not allowed
            throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                    +" creators: already had explicitly marked "+oldOne+", encountered "+newOne);
            // otherwise, which one to choose?
            // new type more generic, use old
            // new type more specific, use it
        }
    }
    if (explicit) {
        _explicitCreators |= mask;
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
