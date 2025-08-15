protected void verifyNonDup(AnnotatedWithParams newOne, int typeIndex, boolean explicit)
{
    final int mask = (1 << typeIndex);
    _hasNonDefaultCreator = true;
    AnnotatedWithParams oldOne = _creators[typeIndex];
    // already had an explicitly marked one?
    if (oldOne != null) {

        if ((_explicitCreators & mask) != 0) { // already had explicitly annotated, leave as-is if new one not explicit
            if (!explicit) {
                return;
            }
            // if both are explicit, conflict check below
        } else if (!explicit) {
            // if neither old nor new is explicit, conflict check below
        } else {
            // old was not explicit but new is explicit:
            // allow new explicit to override old non-explicit
            _creators[typeIndex] = _fixAccess(newOne);
            _explicitCreators |= mask;
            return;
        }

        // both explicit or both not explicit: verify duplication further
        if (oldOne.getClass() == newOne.getClass()) {
            // [databind#667]: avoid one particular class of bogus problems
            throw new IllegalArgumentException("Conflicting " + TYPE_DESCS[typeIndex]
                    + " creators: already had explicitly marked " + oldOne + ", encountered " + newOne);
        }
        // otherwise, determine which type to choose could be added here, but left out for now
    }

    if (explicit) {
        _explicitCreators |= mask;
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
