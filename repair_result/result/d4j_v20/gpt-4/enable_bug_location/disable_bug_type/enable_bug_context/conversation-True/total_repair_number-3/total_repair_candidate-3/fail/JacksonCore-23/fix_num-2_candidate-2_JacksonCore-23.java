public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Must override createInstance() when sub-classing DefaultPrettyPrinter");
    }
    return new DefaultPrettyPrinter(this);
}
