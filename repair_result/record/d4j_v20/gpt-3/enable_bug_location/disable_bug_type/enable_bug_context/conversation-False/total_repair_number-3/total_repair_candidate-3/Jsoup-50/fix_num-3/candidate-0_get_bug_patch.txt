static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // look for BOM - overrides any other header or input
    byteData.mark();
    if (byteData.remaining() >= 3) {
        int b1 = byteData.get() & 0xFF;
        int b2 = byteData.get() & 0xFF;
        int b3 = byteData.get() & 0xFF;
        byteData.reset();

        // UTF-8 BOM (EF BB BF)
        if (b1 == 0xEF && b2 == 0xBB && b3 == 0xBF) {
            charsetName = "UTF-8";
            byteData.position(byteData.position() + 3);
        } 
        // UTF-16 BE BOM (FE FF)
        else if (b1 == 0xFE && b2 == 0xFF) {
            charsetName = "UTF-16BE";
            byteData.position(byteData.position() + 2);
        } 
        UTF-16 LE BOM (FF FE)
        else if (b1 == 0xFF && b2 == 0xFE) {
            charsetName = "UTF-16LE";
            byteData.position(byteData.position() + 2);
        } 
        // UTF-32 BE BOM (00 00 FE FF)
        else if (byteData.remaining() >= 4) {
            int b4 = byteData.get(3) & 0xFF;
            if (b1 == 0x00 && b2 == 0x00 && b3 == 0xFE && b4 == 0xFF) {
                charsetName = "UTF-32BE";
                byteData.position(byteData.position() + 4);
            }
            // UTF-32 LE BOM (FF FE 00 00)
            else if (b1 == 0xFF && b2 == 0xFE && b3 == 0x00 && b4 == 0x00) {
                charsetName = "UTF-32LE";
                byteData.position(byteData.position() + 4);
            }
        }
    }

    if (charsetName == null) { // determine from meta. safe parse as UTF-8
        charsetName = defaultCharset;
        // decode with default charset first
        byteData.rewind();
        docData = Charset.forName(charsetName).decode(byteData).toString();
        doc = parser.parseInput(docData, baseUri);
        Element meta = doc.select("meta[http-equiv=content-type], meta[charset]").first();
        if (meta != null) { // if not found, will keep utf-8 as best attempt
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
    } else { // specified by content type header (or by user on file load)
        Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
        byteData.rewind();
        docData = Charset.forName(charsetName).decode(byteData).toString();
    }

    // Remove BOM char if present at start of data (not handled by above, e.g. UTF-8 BOM)
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
