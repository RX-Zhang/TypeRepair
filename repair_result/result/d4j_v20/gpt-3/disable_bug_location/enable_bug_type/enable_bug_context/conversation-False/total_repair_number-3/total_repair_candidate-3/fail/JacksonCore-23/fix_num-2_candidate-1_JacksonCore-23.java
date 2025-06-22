public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Subclasses must override createInstance()");
    }
    return new DefaultPrettyPrinter(this);
}
