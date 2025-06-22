public Map<String, Integer> getHeaderMap() {
    if (this.headerMap == null) {
        try {
            return initializeHeader();
        } catch (IOException e) {
            throw new RuntimeException("Error initializing header", e);
        }
    }
    return new LinkedHashMap<String, Integer>(this.headerMap);
}
