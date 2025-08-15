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
            // no conflict if different classes, allow override
        } else {
            // no explicit old creator, but new one explicit, so replace old
            if (explicit) {
                _explicitCreators |= mask;
            } else {
                // neither explicit, but old already present, consider if they are same class
                if (oldOne.getClass() == newOne.getClass()) {
                    // conflict: two implied creators of same kind
                    throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                        +" creators: already had implicitly marked "+oldOne+", encountered "+newOne);
                }
                // else allow override
            }
        }
    } else {
        if (explicit) {
            _explicitCreators |= mask;
        }
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
