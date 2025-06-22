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
                // Remove headers that match the null string if applicable
                if (this.format.getNullString() != null) {
                    String nullStr = this.format.getNullString();
                    for (int i = 0; i < headerRecord.length; i++) {
                        if (headerRecord[i] != null && headerRecord[i].equals(nullStr)) {
                            headerRecord[i] = null;
                        }
                    }
                }
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
                final boolean containsHeader = header != null && hdrMap.containsKey(header);
                final boolean emptyHeader = header == null || header.trim().isEmpty();
                if (containsHeader && (!emptyHeader || (emptyHeader && !this.format.getIgnoreEmptyHeaders()))) {
                    throw new IllegalArgumentException("The header contains a duplicate name: \"" + header +
                            "\" in " + Arrays.toString(headerRecord));
                }
                if (header != null) {
                    hdrMap.put(header, Integer.valueOf(i));
                } else if (!this.format.getIgnoreEmptyHeaders()) {
                    // If null header and ignoreEmptyHeaders is false, put empty string key as header
                    hdrMap.put("", Integer.valueOf(i));
                }
            }
        }
    }
    return hdrMap;
}
