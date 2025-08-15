static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // look for BOM - overrides any other header or input
    if (charsetName == null) { // determine from meta. safe parse as UTF-8
        // decode with defaultCharset first to find meta charset info
        byteData.mark();
        docData = Charset.forName(defaultCharset).decode(byteData).toString();
        doc = parser.parseInput(docData, baseUri);
        Element meta = doc.select("meta[http-equiv=content-type], meta[charset]").first();
        if (meta != null) { // if not found, keep defaultCharset as best attempt
            String foundCharset = null;
            if (meta.hasAttr("http-equiv")) {
                foundCharset = getCharsetFromContentType(meta.attr("content"));
            }
            if (foundCharset == null && meta.hasAttr("charset")) {
                try {
                    String charset = meta.attr("charset");
                    if (Charset.isSupported(charset)) {
                        foundCharset = charset;
                    }
                } catch (IllegalCharsetNameException e) {
                    foundCharset = null;
                }
            }

            if (foundCharset != null && foundCharset.length() != 0 && !foundCharset.equalsIgnoreCase(defaultCharset)) {
                foundCharset = foundCharset.trim().replaceAll("[\"']", "");
                charsetName = foundCharset;
                byteData.reset();
                docData = Charset.forName(charsetName).decode(byteData).toString();
                doc = null;
            } else {
                byteData.reset();
            }
        } else {
            byteData.reset();
        }
    } else { // specified by content type header (or by user on file load)
        Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
        byteData.mark();
        docData = Charset.forName(charsetName).decode(byteData).toString();
    }

    // handle BOM character at start of string (if present)
    if (docData.length() > 0 && docData.charAt(0) == UNICODE_BOM) {
        byteData.rewind();
        docData = Charset.forName(charsetName != null ? charsetName : defaultCharset).decode(byteData).toString();
        docData = docData.substring(1); // skip BOM
        charsetName = charsetName != null ? charsetName : defaultCharset;
        doc = null;
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName != null ? charsetName : defaultCharset);
    }
    return doc;
}
