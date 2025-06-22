static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // look for BOM - overrides any other header or input
    byteData.mark();
    if (byteData.remaining() >= 4) {
        int first = byteData.get() & 0xFF;
        int second = byteData.get() & 0xFF;
        int third = byteData.get() & 0xFF;
        int fourth = byteData.get() & 0xFF;
        byteData.reset();

        // UTF-8 BOM: EF BB BF
        if (first == 0xEF && second == 0xBB && third == 0xBF) {
            byteData.position(byteData.position() + 3);
            charsetName = "UTF-8";
        }
        // UTF-16BE BOM: FE FF
        else if (first == 0xFE && second == 0xFF) {
            byteData.position(byteData.position() + 2);
            charsetName = "UTF-16BE";
        }
        // UTF-16LE BOM: FF FE
        else if (first == 0xFF && second == 0xFE) {
            byteData.position(byteData.position() + 2);
            charsetName = "UTF-16LE";
        }
        // UTF-32BE BOM: 00 00 FE FF
        else if (first == 0x00 && second == 0x00 && third == 0xFE && fourth == 0xFF) {
            byteData.position(byteData.position() + 4);
            charsetName = "UTF-32BE";
        }
        // UTF-32LE BOM: FF FE 00 00
        else if (first == 0xFF && second == 0xFE && third == 0x00 && fourth == 0x00) {
            byteData.position(byteData.position() + 4);
            charsetName = "UTF-32LE";
        }
    }

    if (charsetName == null) { // determine from meta. safe parse as UTF-8
        charsetName = defaultCharset;
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

            if (foundCharset != null && foundCharset.length() != 0 && !foundCharset.equalsIgnoreCase(charsetName)) { // need to re-decode
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

    if (docData.length() > 0 && docData.charAt(0) == UNICODE_BOM) {
        docData = docData.substring(1);
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        if (charsetName != null) {
            doc.outputSettings().charset(charsetName);
        }
    }
    return doc;
}
