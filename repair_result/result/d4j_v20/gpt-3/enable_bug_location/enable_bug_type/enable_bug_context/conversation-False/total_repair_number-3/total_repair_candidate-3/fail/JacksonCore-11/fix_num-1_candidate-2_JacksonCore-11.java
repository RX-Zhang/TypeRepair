private void _verifySharing()
{
    if (_hashShared) {
        _hashArea = Arrays.copyOf(_hashArea, _hashArea.length);
        _names = Arrays.copyOf(_names, _names.length);
        _hashShared = false;
    }
    if (_needRehash) {
        try {
            rehash();
        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle the case where the array access might be out of bounds
            throw new RuntimeException("Rehash failed due to array index overflow", e);
        }
    }
}
