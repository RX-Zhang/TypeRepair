private void _verifySharing()
{
    if (_hashShared) {
        _hashArea = Arrays.copyOf(_hashArea, _hashArea.length);
        _names = Arrays.copyOf(_names, _names.length);
        _hashShared = false;
        // 09-Sep-2015, tatu: As per [jackson-core#216], also need to ensure
        //    we rehash as needed, as need-rehash flag is not copied from parent
    }
    if (_needRehash) {
        try {
            rehash();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Defensive handling to avoid runtime crash due to invalid offset access
            // Log or handle as needed, here we just nuke symbols to recover
            nukeSymbols(true);
            _needRehash = false;
        }
    }
}
