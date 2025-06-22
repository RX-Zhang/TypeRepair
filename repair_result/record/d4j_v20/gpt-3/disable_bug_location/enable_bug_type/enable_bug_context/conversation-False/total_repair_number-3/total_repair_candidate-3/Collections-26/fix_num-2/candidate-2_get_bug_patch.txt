private Object readResolve() {
    // Recalculate and assign the hashCode field with the calculated hash code value
    hashCode = calculateHashCode(keys);
    return this;
}

private int calculateHashCode(final Object[] keys)
{
    int total = 0;
    for (final Object key : keys) {
        if (key != null) {
            total ^= key.hashCode();
        }
    }
    return total;
}
