public Map<String, Integer> getHeaderMap() {
    if (this.headerMap == null) {
        try {
            Map<String, Integer> initializedHeader = initializeHeader();
            if (initializedHeader != null) {
                return new LinkedHashMap<String, Integer>(initializedHeader);
            } else {
                return null;
            }
        } catch (IOException e) {
            // Handle or log the exception as needed
            return null;
        }
    }
    return new LinkedHashMap<String, Integer>(this.headerMap);
}
