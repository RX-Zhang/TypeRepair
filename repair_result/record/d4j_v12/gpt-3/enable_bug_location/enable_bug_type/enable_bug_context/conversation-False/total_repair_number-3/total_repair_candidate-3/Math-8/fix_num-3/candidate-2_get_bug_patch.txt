public T[] sample(int sampleSize) throws NotStrictlyPositiveException {
    if (sampleSize <= 0) {
        throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES,
                sampleSize);
    }

    // Ensure that singletons is not empty to avoid ArrayStoreException
    if (singletons.isEmpty()) {
        throw new IllegalStateException("Singletons list is empty.");
    }

    // Get the class of the first element in singletons to create the array
    final Class<?> clazz = singletons.get(0).getClass();
    final T[] out = (T[]) java.lang.reflect.Array.newInstance(clazz, sampleSize);

    for (int i = 0; i < sampleSize; i++) {
        out[i] = sample();
    }

    return out;
}
