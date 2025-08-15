@Override
public DefaultPrettyPrinter createInstance() {
    if (getClass() != DefaultPrettyPrinter.class) {
        throw new IllegalStateException("Sub-class " + getClass().getName() + " must override version of createInstance()");
    }
    return new DefaultPrettyPrinter(this);
}
