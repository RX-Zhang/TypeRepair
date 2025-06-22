private Object readResolve() {
    calculateHashCode((Object[]) keys);
    return this;
}
