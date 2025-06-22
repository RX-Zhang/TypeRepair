<M extends Map<String, String>> M putIn(final M map) {
    for (final Entry<String, Integer> entry : mapping.entrySet()) {
        final int col = entry.getValue().intValue();
        map.put(entry.getKey(), col < values.length ? values[col] : null);
    }
    return map;
}
