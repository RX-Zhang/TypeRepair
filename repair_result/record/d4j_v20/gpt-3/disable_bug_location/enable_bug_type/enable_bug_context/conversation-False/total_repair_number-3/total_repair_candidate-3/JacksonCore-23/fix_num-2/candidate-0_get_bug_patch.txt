public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Sub-classes must override 'createInstance()' to avoid incorrect behavior.");
    }
    return new DefaultPrettyPrinter(this);
}
