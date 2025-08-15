public Attribute(String key, String val, Attributes parent) {
    Validate.notNull(key);
    this.key = null;
    setKey(key);
    this.val = val;
    this.parent = parent;
}
