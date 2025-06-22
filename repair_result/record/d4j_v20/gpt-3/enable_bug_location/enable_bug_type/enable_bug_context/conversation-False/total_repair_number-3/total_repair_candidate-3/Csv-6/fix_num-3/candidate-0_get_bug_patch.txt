<M extends Map<String, String>> M putIn(final M map) {
    for (final Entry<String, Integer> entry : mapping.entrySet()) {
        final int col = entry.getValue().intValue();
        // Adding boundary check to prevent ArrayIndexOutOfBoundsException
        if (col >= 0 && col < values.length) {
            map.put(entry.getKey(), values[col]);
        } else {
            // Handle the case where the index is out of bounds
            map.put(entry.getKey(), null); // or some default value
        }
    }
    return map;
}
