public int[] getCounts(int index) {
    if (index < 0 || index >= totalSize) {
        throw new OutOfRangeException(index, 0, totalSize);
    }

    final int[] indices = new int[dimension];
    int remaining = index;

    for (int i = 0; i < last; i++) {
        int offset = uniCounterOffset[i];
        int idx = remaining / offset;
        remaining = remaining % offset;
        indices[i] = idx;
    }

    indices[last] = remaining;

    return indices;
}
