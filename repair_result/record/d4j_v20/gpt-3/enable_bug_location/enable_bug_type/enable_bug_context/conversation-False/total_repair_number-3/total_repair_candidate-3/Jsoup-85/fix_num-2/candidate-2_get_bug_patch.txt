public Attribute(String key, String val, Attributes parent) {
    Validate.notNull(key, "Key cannot be null");
    key = key.trim();
    Validate.notEmpty(key, "Key cannot be empty"); // trimming could potentially make empty, so validate here
    this.val = val;
    this.parent = parent;
}
