private void _verifySharing()
{
    if (_hashShared) {
        _hashArea = Arrays.copyOf(_hashArea, _hashArea.length);
        _names = Arrays.copyOf(_names, _names.length);
        _hashShared = false;
        _needRehash = false; // Reset rehash flag explicitly when unsharing
    }
    if (_needRehash) {
        rehash();
    }
}
