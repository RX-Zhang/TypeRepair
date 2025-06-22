@Override
public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("createInstance() must be overridden by subclass");
    }
    return new DefaultPrettyPrinter(this);
}
