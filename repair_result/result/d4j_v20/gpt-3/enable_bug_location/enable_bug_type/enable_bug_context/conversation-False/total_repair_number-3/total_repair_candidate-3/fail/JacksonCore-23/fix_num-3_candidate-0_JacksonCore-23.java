public DefaultPrettyPrinter createInstance() {
    if (!(this instanceof MyPrettyPrinter)) {
        throw new IllegalStateException("This class does not override createInstance");
    }
    return new DefaultPrettyPrinter(this);
}
