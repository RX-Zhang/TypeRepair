public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Sub-classes that override createInstance() must override it properly");
    }
    return new DefaultPrettyPrinter(this);
}
