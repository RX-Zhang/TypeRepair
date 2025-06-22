public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Must override createInstance() in sub-class");
    }
    return new DefaultPrettyPrinter(this);
}
