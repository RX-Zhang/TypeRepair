private Object readResolve() {
    calculateHashCode(keys); // Ensure keys are properly initialized before calculating hash code
    return this;
}
