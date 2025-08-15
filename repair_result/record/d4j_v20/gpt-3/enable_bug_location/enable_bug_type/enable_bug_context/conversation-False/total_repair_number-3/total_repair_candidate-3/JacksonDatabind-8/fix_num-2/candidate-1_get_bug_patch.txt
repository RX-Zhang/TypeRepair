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
            // if both explicit: verify conflict
            if (explicit) {
                // one more thing: ok to override in sub-class only if classes differ
                if (oldOne.getClass() == newOne.getClass()) {
                    throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                            +" creators: already had explicitly marked "+oldOne+", encountered "+newOne);
                }
            }
            // if neither explicit, verification below applies
        } else {
            // oldOne was NOT explicit but newOne is explicit: override oldOne with new one
            if (explicit) {
                _explicitCreators |= mask;
                _creators[typeIndex] = _fixAccess(newOne);
                return;
            }
            // else old one non-explicit, new one non-explicit: allow override
        }

        // Here, oldOne exists, newOne exists, both non-explicit OR handled above
        // Avoid clashes if classes are the same, and conflict exists
        if (oldOne.getClass() == newOne.getClass()) {
            throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                    +" creators: already had "+oldOne+", encountered "+newOne);
        }
        // Prefer to keep oldOne if newOne less specific; else override
        // but no automatic resolution here, so keep oldOne
        return;
    }

    if (explicit) {
        _explicitCreators |= mask;
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
