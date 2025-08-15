public Map<String, Integer> getHeaderMap() {
    if (this.headerMap == null) {
        return null; // Return null if headerMap is not initialized
    }
    return new LinkedHashMap<String, Integer>(this.headerMap);
}
