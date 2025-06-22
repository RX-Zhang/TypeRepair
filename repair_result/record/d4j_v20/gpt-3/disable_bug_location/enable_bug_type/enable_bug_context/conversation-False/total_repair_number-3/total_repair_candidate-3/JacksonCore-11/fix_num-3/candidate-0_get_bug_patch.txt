private void _verifySharing()
{
    if (_hashShared) {
        _hashArea = Arrays.copyOf(_hashArea, _hashArea.length);
        _names = Arrays.copyOf(_names, _names.length);
        _hashShared = false;
        // 09-Sep-2015, tatu: As per [jackson-core#216], also need to ensure
        //    we rehash as needed, as need-rehash flag is not copied from parent
        // Add this to trigger rehash if needed after copying
        _needRehash = true;
    }
    if (_needRehash) {
        try {
            rehash();
        } catch (Exception e) {
            // In case rehash fails (e.g. due to OOME), handle gracefully
            // Could log or handle differently depending on context
            // For now, just disable needRehash to prevent infinite loops
            _needRehash = false;
        }
    }
}
