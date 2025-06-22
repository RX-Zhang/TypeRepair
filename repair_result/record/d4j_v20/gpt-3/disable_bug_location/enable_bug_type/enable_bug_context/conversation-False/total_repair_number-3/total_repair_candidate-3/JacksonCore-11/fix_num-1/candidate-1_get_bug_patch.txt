private void _verifySharing()
{
    if (_hashShared) {
        _hashArea = Arrays.copyOf(_hashArea, _hashArea.length);
        _names = Arrays.copyOf(_names, _names.length);
        _hashShared = false;
        // 09-Sep-2015, tatu: As per [jackson-core#216], also need to ensure
        //    we rehash as needed, as need-rehash flag is not copied from parent
    }
    try {
        if (_needRehash) {
            rehash();
        }
    } catch (ArrayIndexOutOfBoundsException e) {
        // Defensive catch for possible array overflow during rehash
        // Could log or rethrow as needed; for now we mark needRehash false to avoid loop
        _needRehash = false;
        throw e;
    }
}
