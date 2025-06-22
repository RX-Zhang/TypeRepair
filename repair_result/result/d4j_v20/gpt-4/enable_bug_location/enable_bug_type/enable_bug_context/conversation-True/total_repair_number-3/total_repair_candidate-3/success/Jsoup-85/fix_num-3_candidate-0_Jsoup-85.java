public Attribute(String key, String val, Attributes parent) {
    Validate.notNull(key);
    setKey(key);
    this.val = val;
    this.parent = parent;
}
