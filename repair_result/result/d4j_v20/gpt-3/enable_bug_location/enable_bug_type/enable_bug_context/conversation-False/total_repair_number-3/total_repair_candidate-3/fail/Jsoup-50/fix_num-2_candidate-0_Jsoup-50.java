static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // look for BOM - overrides any other header or input
    // Check for BOM at the start of byteData
    byteData.mark();
    if (byteData.remaining() >= 3) {
        int b1 = byteData.get() & 0xFF;
        int b2 = byteData.get() & 0xFF;
        int b3 = byteData.get() & 0xFF;
        byteData.reset();

        // UTF-8 BOM (EF BB BF)
        if (b1 == 0xEF && b2 == 0xBB && b3 == 0xBF) {
            charsetName = "UTF-8";
            // Skip BOM bytes
            byteData.position(byteData.position() + 3);
        } else if (byteData.remaining() >= 4) {
            // Check for UTF-32 BOMs
            int b4 = byteData.get(3) & 0xFF;
            // UTF-32BE BOM (00 00 FE FF)
            if (b1 == 0x00 && b2 == 0x00 && b3 == 0xFE && b4 == 0xFF) {
                charsetName = "UTF-32BE";
                byteData.position(byteData.position() + 4);
            }
            // UTF-32LE BOM (FF FE 00 00)
            else if (b1 == 0xFF && b2 == 0xFE && b3 == 0x00 && b4 == 0x00) {
                charsetName = "UTF-32LE";
                byteData.position(byteData.position() + 4);
            }
            // else leave position unchanged
            else {
                byteData.reset();
            }
        } else if (byteData.remaining() >= 2) {
            // Check for UTF-16 BOMs
            if (b1 == 0xFE && b2 == 0xFF) { // UTF-16BE
                charsetName = "UTF-16BE";
                byteData.position(byteData.position() + 2);
            } else if (b1 == 0xFF && b2 == 0xFE) { // UTF-16LE
                charsetName = "UTF-16LE";
                byteData.position(byteData.position() + 2);
            } else {
                byteData.reset();
            }
        } else {
            byteData.reset();
        }
    } else {
        byteData.reset();
    }

    // If charset not specified or was null, use defaultCharset for initial decode
    if (charsetName == null) { 
        charsetName = defaultCharset;
    }

    docData = Charset.forName(charsetName).decode(byteData).toString();

    // If charset was not specified and BOM was not found, look for meta charset tag
    if (charsetName.equals(defaultCharset)) {
        doc = parser.parseInput(docData, baseUri);
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
            if (foundCharset != null && foundCharset.length() != 0 && !foundCharset.equalsIgnoreCase(charsetName)) { // need to re-decode
                foundCharset = foundCharset.trim().replaceAll("[\"']", "");
                try {
                    Charset.forName(foundCharset); // validate charset
                    charsetName = foundCharset;
                    byteData.rewind();
                    docData = Charset.forName(foundCharset).decode(byteData).toString();
                    doc = null; // force re-parse with new charset
                } catch (UnsupportedCharsetException e) {
                    // keep previous charset and doc
                }
            }
        }
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
    }

    return doc;
}
