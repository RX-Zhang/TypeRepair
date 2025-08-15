public Map<String, Integer> getHeaderMap() {
    if (this.headerMap == null) {
        try {
            Map<String, Integer> initializedHeaderMap = initializeHeader();
            if (initializedHeaderMap == null) {
                return null;
            }
            return new LinkedHashMap<String, Integer>(initializedHeaderMap);
        } catch (IOException e) {
            // Depending on the design, either return null or rethrow as unchecked exception
            return null;
        }
    }
    return new LinkedHashMap<String, Integer>(this.headerMap);
}
