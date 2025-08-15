private Map<String, Integer> initializeHeader() throws IOException {
    Map<String, Integer> hdrMap = null;
    final String[] formatHeader = this.format.getHeader();
    if (formatHeader != null) {
        hdrMap = new LinkedHashMap<String, Integer>();

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
                // Handle null header to avoid NullPointerException
                final boolean containsHeader = header != null && hdrMap.containsKey(header);
                final boolean emptyHeader = header == null || header.trim().isEmpty();
                if (containsHeader && (!emptyHeader || (emptyHeader && !this.format.getIgnoreEmptyHeaders()))) {
                    throw new IllegalArgumentException("The header contains a duplicate name: \"" + header +
                            "\" in " + Arrays.toString(headerRecord));
                }
                // Use empty string for null header if ignoring empty headers
                final String key = header == null ? "" : header;
                hdrMap.put(key, Integer.valueOf(i));
            }
        }
    }
    return hdrMap;
}
