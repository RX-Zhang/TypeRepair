public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Sub-classes must override createInstance()");
    }
    return new DefaultPrettyPrinter(this);
}
