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
                // no header record found, avoid NullPointerException later
                return null;
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
                // Protect against null header values causing NullPointerException
                if (header == null) {
                    if (!this.format.getIgnoreEmptyHeaders()) {
                        throw new IllegalArgumentException("Header contains null header name in " + Arrays.toString(headerRecord));
                    } else {
                        continue;
                    }
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
    }
    return hdrMap;
}
