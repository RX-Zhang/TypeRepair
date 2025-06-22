static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // look for BOM - overrides any other header or input
    // Check for BOM and detect charset before parsing meta

    // Mark position for resetting after BOM detection
    byteData.mark();

    // Decode first bytes to check for BOM (simple detection for UTF-8, UTF-16, UTF-32 BOM)
    // Reading BOM bytes to identify BOM charset if present
    Charset bomCharset = null;
    int bomLength = 0;

    if (byteData.remaining() >= 4) {
        int b1 = byteData.get(0) & 0xFF;
        int b2 = byteData.get(1) & 0xFF;
        int b3 = byteData.get(2) & 0xFF;
        int b4 = byteData.get(3) & 0xFF;

        if (b1 == 0x00 && b2 == 0x00 && b3 == 0xFE && b4 == 0xFF) {
            // UTF-32BE BOM
            bomCharset = Charset.forName("UTF-32BE");
            bomLength = 4;
        } else if (b1 == 0xFF && b2 == 0xFE && b3 == 0x00 && b4 == 0x00) {
            // UTF-32LE BOM
            bomCharset = Charset.forName("UTF-32LE");
            bomLength = 4;
        } else if (b1 == 0xEF && b2 == 0xBB && b3 == 0xBF) {
            // UTF-8 BOM
            bomCharset = Charset.forName("UTF-8");
            bomLength = 3;
        } else if (b1 == 0xFE && b2 == 0xFF) {
            // UTF-16BE BOM
            bomCharset = Charset.forName("UTF-16BE");
            bomLength = 2;
        } else if (b1 == 0xFF && b2 == 0xFE) {
            // UTF-16LE BOM
            bomCharset = Charset.forName("UTF-16LE");
            bomLength = 2;
        }
    } else if (byteData.remaining() >= 3) {
        int b1 = byteData.get(0) & 0xFF;
        int b2 = byteData.get(1) & 0xFF;
        int b3 = byteData.get(2) & 0xFF;
        if (b1 == 0xEF && b2 == 0xBB && b3 == 0xBF) {
            // UTF-8 BOM
            bomCharset = Charset.forName("UTF-8");
            bomLength = 3;
        }
    } else if (byteData.remaining() >= 2) {
        int b1 = byteData.get(0) & 0xFF;
        int b2 = byteData.get(1) & 0xFF;
        if (b1 == 0xFE && b2 == 0xFF) {
            // UTF-16BE BOM
            bomCharset = Charset.forName("UTF-16BE");
            bomLength = 2;
        } else if (b1 == 0xFF && b2 == 0xFE) {
            // UTF-16LE BOM
            bomCharset = Charset.forName("UTF-16LE");
            bomLength = 2;
        }
    }

    if (bomCharset != null) {
        // If BOM detected, read using BOM charset and skip BOM bytes
        byteData.position(bomLength);
        int limit = byteData.limit();
        ByteBuffer slice = byteData.slice();
        slice.limit(limit - bomLength);
        docData = bomCharset.decode(slice).toString();
        charsetName = bomCharset.name();
        doc = parser.parseInput(docData, baseUri);
        return doc.outputSettings().charset(charsetName) == null ? doc : doc;
    }

    // No BOM detected, reset position
    byteData.reset();

    if (charsetName == null) { // determine from meta. safe parse as UTF-8
        // look for <meta http-equiv="Content-Type" content="text/html;charset=gb2312"> or HTML5 <meta charset="gb2312">
        docData = Charset.forName(defaultCharset).decode(byteData).toString();
        doc = parser.parseInput(docData, baseUri);
        Element meta = doc.select("meta[http-equiv=content-type], meta[charset]").first();
        if (meta != null) { // if not found, will keep utf-8 as best attempt
            String foundCharset = null;
            if (meta.hasAttr("http-equiv")) {
                foundCharset = getCharsetFromContentType(meta.attr("content"));
            }
            if (foundCharset == null && meta.hasAttr("charset")) {
                try {
                    String metaCharset = meta.attr("charset");
                    if (Charset.isSupported(metaCharset)) {
                        foundCharset = metaCharset;
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
    } else { // specified by content type header (or by user on file load)
        Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
        docData = Charset.forName(charsetName).decode(byteData).toString();
    }

    // handle any unexpected BOM char in decoded data
    if (docData.length() > 0 && docData.charAt(0) == UNICODE_BOM) {
        docData = docData.substring(1);
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
    }

    return doc;
}
