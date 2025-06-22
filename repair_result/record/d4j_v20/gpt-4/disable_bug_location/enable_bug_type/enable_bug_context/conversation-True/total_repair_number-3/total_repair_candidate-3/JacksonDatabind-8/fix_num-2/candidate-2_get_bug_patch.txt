protected void verifyNonDup(AnnotatedWithParams newOne, int typeIndex, boolean explicit)
{
    final int mask = (1 << typeIndex);
    _hasNonDefaultCreator = true;
    AnnotatedWithParams oldOne = _creators[typeIndex];
    // already had an explicitly marked one?
    if (oldOne != null) {

        if ((_explicitCreators & mask) != 0) { // already had explicitly annotated
            // skip if new one not annotated explicitly
            if (!explicit) {
                return;
            }
            // if new one explicit, both explicit: verify conflict below
        } else {
            // old one not explicit, if new is not explicit too, verify conflict below
            // if new is explicit, allow override
            if (explicit) {
                // override old explicit flag
                _explicitCreators |= mask;
            }
            // else none explicit, verify conflict below
        }

        // verify conflict if both explicit or both non-explicit
        if (oldOne.getClass() == newOne.getClass()) {
            throw new IllegalArgumentException("Conflicting "+TYPE_DESCS[typeIndex]
                    +" creators: already had explicitly marked "+oldOne+", encountered "+newOne);
        }
    } else {
        if (explicit) {
            _explicitCreators |= mask;
        }
    }
    _creators[typeIndex] = _fixAccess(newOne);
}
