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
        // Add boundary check to avoid ArrayIndexOutOfBoundsException during rehash
        if (_hashArea != null && _hashArea.length > 0) {
            rehash();
        } else {
            // If hashArea is null or empty, set _needRehash to false to avoid rehash attempts
            _needRehash = false;
        }
    }
}
