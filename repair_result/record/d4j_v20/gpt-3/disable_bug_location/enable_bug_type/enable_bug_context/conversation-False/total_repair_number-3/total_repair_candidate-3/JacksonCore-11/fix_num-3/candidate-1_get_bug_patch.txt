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
            // Defensive: in case rehash triggers unexpected array bounds errors,
            // nuke symbols to avoid inconsistent state.
            nukeSymbols(true);
        } catch (Exception e) {
            // Catch any other runtime exceptions to avoid crash during rehash
            nukeSymbols(true);
        }
    }
}
