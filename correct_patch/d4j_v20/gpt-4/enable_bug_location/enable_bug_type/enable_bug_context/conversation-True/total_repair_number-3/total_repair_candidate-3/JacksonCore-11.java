private void _verifySharing()
{
    if (_hashShared) {
        _hashArea = Arrays.copyOf(_hashArea, _hashArea.length);
        _names = Arrays.copyOf(_names, _names.length);
        _hashShared = false;
        _verifyNeedForRehash(); // added to update _needRehash flag
    }
    if (_needRehash) {
        rehash();
    }
}
