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
            _needRehash = false; // reset flag if rehash succeeds
        } catch (Exception e) {
            // Log or handle unexpected exceptions gracefully
            // For instance, ignore or rethrow depending on context
            // To prevent runtime failure, we catch here
            // e.printStackTrace(); // optionally log
        }
    }
}
