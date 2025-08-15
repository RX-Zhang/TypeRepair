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

        // No explicit oldOne, but newOne is explicit -> override
        // If neither explicit, allow override as well
        if (!explicit) {
            // neither explicit, allow override
            _creators[typeIndex] = _fixAccess(newOne);
            return;
        }
    }
    if (explicit) {
        _explicitCreators |= mask;
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
