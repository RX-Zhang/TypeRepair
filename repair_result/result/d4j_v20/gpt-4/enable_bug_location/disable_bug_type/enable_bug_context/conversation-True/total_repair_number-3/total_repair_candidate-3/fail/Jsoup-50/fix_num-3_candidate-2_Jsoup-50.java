static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // Look for BOM in the byte data
    if (charsetName == null) { // determine charset from meta, safe parse as UTF-8 (defaultCharset)
        // Decode as defaultCharset first (UTF-8)
        byteData.mark();
        docData = Charset.forName(defaultCharset).decode(byteData).toString();
        doc = parser.parseInput(docData, baseUri);
        Element meta = doc.select("meta[http-equiv=content-type], meta[charset]").first();
        if (meta != null) { // If meta tag specifies charset, try to get it
            String foundCharset = null;
            if (meta.hasAttr("http-equiv")) {
                foundCharset = getCharsetFromContentType(meta.attr("content"));
            }
            if (foundCharset == null && meta.hasAttr("charset")) {
                try {
                    String metaCharset = meta.attr("charset").trim();
                    if (Charset.isSupported(metaCharset)) {
                        foundCharset = metaCharset;
                    }
                } catch (IllegalCharsetNameException e) {
                    foundCharset = null;
                }
            }

            if (foundCharset != null && foundCharset.length() != 0 && !foundCharset.equalsIgnoreCase(defaultCharset)) {
                foundCharset = foundCharset.trim().replaceAll("[\"']", "");
                charsetName = foundCharset;
                byteData.reset();
                docData = Charset.forName(foundCharset).decode(byteData).toString();
                doc = null;
            }
        } else {
            byteData.reset();
        }
    } else {
        Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
        byteData.mark();
        docData = Charset.forName(charsetName).decode(byteData).toString();
    }

    // Check and remove Unicode BOM from decoded string if present
    if (docData.length() > 0 && docData.charAt(0) == UNICODE_BOM) {
        // BOM found, remove it and set charset to defaultCharset
        charsetName = defaultCharset;
        docData = docData.substring(1);
        doc = null;
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
    }

    return doc;
}
