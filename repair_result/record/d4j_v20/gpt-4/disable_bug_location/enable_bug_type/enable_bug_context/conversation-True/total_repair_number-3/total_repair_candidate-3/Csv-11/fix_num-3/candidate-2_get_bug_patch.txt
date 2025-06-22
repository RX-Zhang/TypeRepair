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
                // Defensive: no header record available
                headerRecord = new String[0];
            }
        } else {
            if (this.format.getSkipHeaderRecord()) {
                // Defensive: avoid ignoring header if no records to skip
                try {
                    this.nextRecord();
                } catch (IOException e) {
                    // Log or handle the exception if needed, but do not fail here
                }
            }
            headerRecord = formatHeader;
        }

        // build the name to index mappings
        if (headerRecord != null) {
            for (int i = 0; i < headerRecord.length; i++) {
                final String header = headerRecord[i];
                if (header == null) {
                    throw new IllegalArgumentException("Header name at index " + i + " is null");
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
