public String setValue(String val) {
    if (parent == null) {
        this.val = val;
        return null; // Return null if parent is not set
    }
    
    String oldVal = parent.get(this.key);
    int i = parent.indexOfKey(this.key);
    if (i != Attributes.NotFound) {
        parent.vals[i] = val;
    }
    this.val = val;
    return Attributes.checkNotNull(oldVal);
}
