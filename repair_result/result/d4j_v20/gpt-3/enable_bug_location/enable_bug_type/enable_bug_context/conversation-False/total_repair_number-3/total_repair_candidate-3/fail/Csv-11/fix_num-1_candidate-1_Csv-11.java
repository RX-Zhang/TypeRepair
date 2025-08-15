private Map<String, Integer> initializeHeader() throws IOException {
    Map<String, Integer> hdrMap = new LinkedHashMap<>(); // Initialize hdrMap to avoid NullPointerException
    final String[] formatHeader = this.format.getHeader();
    
    // Input validation for formatHeader
    if (formatHeader == null) {
        throw new IllegalArgumentException("Format header cannot be null");
    }

    String[] headerRecord = null;
    if (formatHeader.length == 0) {
        // read the header from the first line of the file
        final CSVRecord nextRecord = this.nextRecord();
        if (nextRecord != null) {
            headerRecord = nextRecord.values();
        }
    } else {
        if (this.format.getSkipHeaderRecord()) {
            this.nextRecord();
        }
        headerRecord = formatHeader;
    }

    // build the name to index mappings
    if (headerRecord != null) {
        for (int i = 0; i < headerRecord.length; i++) {
            final String header = headerRecord[i];
            // Input validation for header
            if (header == null) {
                throw new IllegalArgumentException("Header cannot be null");
            }
            final boolean containsHeader = hdrMap.containsKey(header);
            final boolean emptyHeader = header.trim().isEmpty();
            if (containsHeader && (!emptyHeader || (emptyHeader && !this.format.getIgnoreEmptyHeaders()))) {
                throw new IllegalArgumentException("The header contains a duplicate name: \"" + header +
                        "\" in " + Arrays.toString(headerRecord));
            }
            hdrMap.put(header, Integer.valueOf(i));
        }
    }
    return hdrMap;
}
