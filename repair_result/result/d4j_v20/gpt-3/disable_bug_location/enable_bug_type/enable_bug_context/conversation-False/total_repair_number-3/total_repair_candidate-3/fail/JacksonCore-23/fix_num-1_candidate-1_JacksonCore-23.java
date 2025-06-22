public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Must override createInstance() when subclassing DefaultPrettyPrinter");
    }
    return new DefaultPrettyPrinter(this);
}
