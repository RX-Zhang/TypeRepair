public String setValue(String val) {
    if (parent != null) {
        String oldVal = parent.get(this.key);
        int i = parent.indexOfKey(this.key);
        if (i != Attributes.NotFound)
            parent.vals[i] = val;
        this.val = val;
        return Attributes.checkNotNull(oldVal);
    } else {
        String oldVal = this.val;
        this.val = val;
        return Attributes.checkNotNull(oldVal);
    }
}
