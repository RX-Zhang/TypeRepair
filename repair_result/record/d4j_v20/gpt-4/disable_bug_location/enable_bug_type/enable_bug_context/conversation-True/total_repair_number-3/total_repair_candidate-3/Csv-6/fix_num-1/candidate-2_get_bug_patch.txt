<M extends Map<String, String>> M putIn(final M map) {
    final int size = size();
    for (final Entry<String, Integer> entry : mapping.entrySet()) {
        final int col = entry.getValue().intValue();
        if (col < size) {
            map.put(entry.getKey(), values[col]);
        } else {
            map.put(entry.getKey(), null);
        }
    }
    return map;
}
