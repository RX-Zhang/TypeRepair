private Object readResolve() {
    // Ensure that the keys are initialized before calculating the hash code
    if (keys != null) {
        calculateHashCode(keys);
    }
    return this;
}
