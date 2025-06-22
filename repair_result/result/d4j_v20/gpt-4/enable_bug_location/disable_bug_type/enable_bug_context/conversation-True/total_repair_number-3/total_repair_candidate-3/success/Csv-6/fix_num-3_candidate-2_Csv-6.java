<M extends Map<String, String>> M putIn(final M map) {
    final int size = size();
    for (final Entry<String, Integer> entry : mapping.entrySet()) {
        final int col = entry.getValue().intValue();
        map.put(entry.getKey(), col < size ? values[col] : null);
    }
    return map;
}
