public Map<String, Integer> getHeaderMap() {
    if (this.headerMap == null) {
        try {
            Map<String, Integer> initializedHeaderMap = initializeHeader();
            if (initializedHeaderMap != null) {
                return new LinkedHashMap<String, Integer>(initializedHeaderMap);
            } else {
                return null;
            }
        } catch (IOException e) {
            // Handle or rethrow as unchecked, depending on design
            throw new RuntimeException("Error initializing header map", e);
        }
    }
    return new LinkedHashMap<String, Integer>(this.headerMap);
}
