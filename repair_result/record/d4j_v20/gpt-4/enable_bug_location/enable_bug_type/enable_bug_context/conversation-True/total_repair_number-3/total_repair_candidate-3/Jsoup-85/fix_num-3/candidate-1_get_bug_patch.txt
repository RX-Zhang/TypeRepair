public Attribute(String key, String val, Attributes parent) {
    Validate.notNull(key);
    setKey(key); // use setKey to trim and validate the key properly
    this.val = val;
    this.parent = parent;
}
