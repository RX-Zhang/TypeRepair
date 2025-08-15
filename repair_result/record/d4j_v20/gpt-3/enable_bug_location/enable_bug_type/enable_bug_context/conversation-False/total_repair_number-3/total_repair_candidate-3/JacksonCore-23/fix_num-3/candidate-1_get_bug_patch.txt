public DefaultPrettyPrinter createInstance() {
    if (!(this instanceof MyPrettyPrinter)) {
        throw new IllegalStateException("does not override");
    }
    return new DefaultPrettyPrinter(this);
}
