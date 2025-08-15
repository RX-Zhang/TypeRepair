public int[] getCounts(int index) {
    if (index < 0 || index >= totalSize) {
        throw new OutOfRangeException(index, 0, totalSize);
    }

    final int[] indices = new int[dimension];
    int remainder = index;

    for (int i = 0; i < last; i++) {
        indices[i] = remainder / uniCounterOffset[i];
        remainder = remainder % uniCounterOffset[i];
    }

    indices[last] = remainder;

    return indices;
}
