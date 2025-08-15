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
            } else {
                // No header record found, avoid NullPointerException
                return hdrMap;
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
                if (header == null) {
                    if (!this.format.getIgnoreEmptyHeaders()) {
                        throw new IllegalArgumentException("The header contains a null value at index: " + i +
                                " in " + Arrays.toString(headerRecord));
                    }
                    // else treat null as empty string key 
                }
                final String normalizedHeader = header == null ? "" : header;
                final boolean emptyHeader = normalizedHeader.trim().isEmpty();
                final boolean containsHeader = hdrMap.containsKey(normalizedHeader);
                if (containsHeader && (!emptyHeader || (emptyHeader && !this.format.getIgnoreEmptyHeaders()))) {
                    throw new IllegalArgumentException("The header contains a duplicate name: \"" + normalizedHeader +
                            "\" in " + Arrays.toString(headerRecord));
                }
                hdrMap.put(normalizedHeader, Integer.valueOf(i));
            }
        }
    }
    return hdrMap;
}
