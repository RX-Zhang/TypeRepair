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
            throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                    +" creators: already had explicitly marked "+oldOne+", encountered "+newOne);
        }

        // If we get here, oldOne was not explicit but newOne is explicit:
        // allow new explicit one to override
        if (explicit) {
            _explicitCreators |= mask;
            _creators[typeIndex] = _fixAccess(newOne);
            return;
        }

        // neither were explicit: allow override if different class type
        if (oldOne.getClass() == newOne.getClass()) {
            throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                    +" creators: already had explicitly marked "+oldOne+", encountered "+newOne);
        }
        // if different classes, override old with new
    }
    if (explicit) {
        _explicitCreators |= mask;
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
