@Override
public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Subclass must override createInstance()");
    }
    return new DefaultPrettyPrinter(this);
}
