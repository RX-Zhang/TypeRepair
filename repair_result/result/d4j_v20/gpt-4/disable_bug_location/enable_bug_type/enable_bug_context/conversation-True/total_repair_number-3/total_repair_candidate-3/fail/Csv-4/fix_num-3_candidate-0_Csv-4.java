public Map<String, Integer> getHeaderMap() {
    if (this.headerMap == null) {
        try {
            return new LinkedHashMap<String, Integer>(initializeHeader());
        } catch (IOException e) {
            // handle the exception as needed, 
            // here we return null or you may log the error
            return null;
        }
    } else {
        return new LinkedHashMap<String, Integer>(this.headerMap);
    }
}
