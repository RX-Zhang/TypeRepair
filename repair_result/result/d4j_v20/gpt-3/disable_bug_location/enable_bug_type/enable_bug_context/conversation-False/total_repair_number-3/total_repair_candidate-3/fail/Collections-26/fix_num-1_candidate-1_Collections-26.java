private Object readResolve() {
    if (keys != null) {
        calculateHashCode(keys);
    } else {
        hashCode = 0;
    }
    return this;
}
