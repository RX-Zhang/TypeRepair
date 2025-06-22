protected void verifyNonDup(AnnotatedWithParams newOne, int typeIndex, boolean explicit)
{
    final int mask = (1 << typeIndex);
    _hasNonDefaultCreator = true;
    AnnotatedWithParams oldOne = _creators[typeIndex];
    // already had an explicitly marked one?
    if (oldOne != null) {

        boolean oldExplicit = (_explicitCreators & mask) != 0;
        // if both old and new are explicit, throw error
        if (oldExplicit && explicit) {
            // one more thing: ok to override in sub-class
            if (oldOne.getClass() == newOne.getClass()) {
                // [databind#667]: avoid one particular class of bogus problems
                throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                        +" creators: already had explicitly marked "+oldOne+", encountered "+newOne);
            }
            // If classes differ, allow override (no exception)
        }
        // If old explicit but new not explicit, keep old and return
        else if (oldExplicit && !explicit) {
            return;
        }
        // If old not explicit and new explicit, override
        // If both non-explicit, continue and override
    }

    if (explicit) {
        _explicitCreators |= mask;
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
