public DefaultPrettyPrinter createInstance() {
    if (this instanceof MyPrettyPrinter) {
        throw new IllegalStateException("MyPrettyPrinter does not override createInstance");
    }
    return new DefaultPrettyPrinter(this);
}
