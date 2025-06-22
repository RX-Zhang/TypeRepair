public DefaultPrettyPrinter createInstance() {
    if (this == null) {
        throw new IllegalStateException("Instance cannot be null");
    }
    return new DefaultPrettyPrinter(this);
}
