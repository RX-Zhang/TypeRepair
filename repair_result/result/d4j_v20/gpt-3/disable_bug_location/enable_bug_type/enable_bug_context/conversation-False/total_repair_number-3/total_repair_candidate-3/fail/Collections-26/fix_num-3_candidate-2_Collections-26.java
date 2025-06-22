private Object readResolve() {
    calculateHashCode(this.keys);
    return this;
}
