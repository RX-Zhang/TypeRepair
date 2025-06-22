public String setValue(String val) {
    if (parent == null) {
        this.val = val;
        return null; // Return null if there is no parent
    }
    
    String oldVal = parent.get(this.key);
    int i = parent.indexOfKey(this.key);
    if (i != Attributes.NotFound) {
        parent.vals[i] = val;
    }
    this.val = val;
    return Attributes.checkNotNull(oldVal);
}
