public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Subclass " + getClass().getName()
                + " does not override createInstance()");
    }
    return new DefaultPrettyPrinter(this);
}
