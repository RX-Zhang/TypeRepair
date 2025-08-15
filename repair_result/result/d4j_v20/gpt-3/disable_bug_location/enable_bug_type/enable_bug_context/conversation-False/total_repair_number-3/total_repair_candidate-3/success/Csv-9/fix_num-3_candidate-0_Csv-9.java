<M extends Map<String, String>> M putIn(final M map) {
    if (mapping == null) {
        return map;
    }
    for (final Entry<String, Integer> entry : mapping.entrySet()) {
        final Integer colIndex = entry.getValue();
        if (colIndex != null) {
            final int col = colIndex.intValue();
            if (col >= 0 && col < values.length) {
                map.put(entry.getKey(), values[col]);
            }
        }
    }
    return map;
}
