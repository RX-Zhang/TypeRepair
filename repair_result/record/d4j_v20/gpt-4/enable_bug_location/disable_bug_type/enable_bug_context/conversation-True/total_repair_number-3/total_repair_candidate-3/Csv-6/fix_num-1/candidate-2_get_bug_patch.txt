<M extends Map<String, String>> M putIn(final M map) {
    for (final Entry<String, Integer> entry : mapping.entrySet()) {
        final String key = entry.getKey();
        final int col = entry.getValue().intValue();
        if (col >= 0 && col < values.length) {
            map.put(key, values[col]);
        } else {
            map.put(key, null);
        }
    }
    return map;
}
