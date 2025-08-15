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
            // both explicit: verify
            if (explicit) {
                // if both explicit, verify conflict
                if (oldOne.getClass() == newOne.getClass()) {
                    throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                            +" creators: already had explicitly marked "+oldOne+", encountered "+newOne);
                } else {
                    // For different creator types, either choose old or new depending on generic/specific
                    // but since we can't tell here, leave old one.
                    return;
                }
            }
            // if old explicit but new explicit is false, we already returned above
            // if neither explicit, fall through to verification below
        } else {
            // was not explicit before
            if (explicit) {
                // new one is explicit, override old one
            } else {
                // both not explicit: verify similar classes to avoid conflicts
                if (oldOne.getClass() == newOne.getClass()) {
                    throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                            +" creators: already had explicitly marked "+oldOne+", encountered "+newOne);
                } else {
                    return;
                }
            }
        }
    }
    if (explicit) {
        _explicitCreators |= mask;
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
