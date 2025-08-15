static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    byteData.mark();  // mark the starting position for potential reset

    // look for BOM - overrides any other header or input
    // Detect BOMs and adjust charset accordingly
    if (charsetName == null) { // determine from meta. safe parse as UTF-8
        byteData.rewind();

        // Check for BOM before decoding
        if (byteData.remaining() >= 4) {
            int b0 = byteData.get() & 0xFF;
            int b1 = byteData.get() & 0xFF;
            int b2 = byteData.get() & 0xFF;
            int b3 = byteData.get() & 0xFF;
            byteData.rewind();

            if (b0 == 0x00 && b1 == 0x00 && b2 == 0xFE && b3 == 0xFF) {
                // UTF-32BE BOM detected
                charsetName = "UTF-32BE";
                byteData.position(4);
            } else if (b0 == 0xFF && b1 == 0xFE && b2 == 0x00 && b3 == 0x00) {
                // UTF-32LE BOM detected
                charsetName = "UTF-32LE";
                byteData.position(4);
            } else if (b0 == 0xFE && b1 == 0xFF) {
                // UTF-16BE BOM detected
                charsetName = "UTF-16BE";
                byteData.position(2);
            } else if (b0 == 0xFF && b1 == 0xFE) {
                // UTF-16LE BOM detected
                charsetName = "UTF-16LE";
                byteData.position(2);
            } else if (b0 == 0xEF && b1 == 0xBB && b2 == 0xBF) {
                // UTF-8 BOM detected
                charsetName = "UTF-8";
                byteData.position(3);
            } else {
                charsetName = defaultCharset;
                byteData.rewind();
            }
        } else {
            charsetName = defaultCharset;
            byteData.rewind();
        }

        Charset cs = Charset.forName(charsetName);
        docData = cs.decode(byteData).toString();
        doc = parser.parseInput(docData, baseUri);

        if (charsetName.equals(defaultCharset)) {
            Element meta = doc.select("meta[http-equiv=content-type], meta[charset]").first();
            if (meta != null) { // if not found, will keep defaultCharset as best attempt
                String foundCharset = null;
                if (meta.hasAttr("http-equiv")) {
                    foundCharset = getCharsetFromContentType(meta.attr("content"));
                }
                if (foundCharset == null && meta.hasAttr("charset")) {
                    try {
                        if (Charset.isSupported(meta.attr("charset"))) {
                            foundCharset = meta.attr("charset");
                        }
                    } catch (IllegalCharsetNameException e) {
                        foundCharset = null;
                    }
                }

                if (foundCharset != null && foundCharset.length() != 0 && !foundCharset.equalsIgnoreCase(defaultCharset)) { // need to re-decode
                    foundCharset = foundCharset.trim().replaceAll("[\"']", "");
                    charsetName = foundCharset;
                    byteData.rewind();
                    docData = Charset.forName(foundCharset).decode(byteData).toString();
                    doc = null;
                }
            }
        }
    } else { // specified by content type header (or by user on file load)
        Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
        byteData.rewind();
        docData = Charset.forName(charsetName).decode(byteData).toString();
    }

    if (docData.length() > 0 && docData.charAt(0) == UNICODE_BOM) {
        docData = docData.substring(1);
        charsetName = charsetName != null ? charsetName : defaultCharset;
        doc = null;
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
    }
    return doc;
}
