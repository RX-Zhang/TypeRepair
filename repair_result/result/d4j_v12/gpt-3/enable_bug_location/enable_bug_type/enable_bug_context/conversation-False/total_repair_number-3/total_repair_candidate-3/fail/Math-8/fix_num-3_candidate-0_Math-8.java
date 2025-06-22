public T[] sample(int sampleSize) throws NotStrictlyPositiveException {
    if (sampleSize <= 0) {
        throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES,
                sampleSize);
    }

    // Verify and convert variable types before performing operations
    Class<?> clazz = singletons.get(0).getClass();
    final T[] out = (T[]) java.lang.reflect.Array.newInstance(clazz, sampleSize);

    for (int i = 0; i < sampleSize; i++) {
        out[i] = sample();
    }

    return out;
}
