static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // look for BOM - overrides any other header or input
    // Check for BOM at the start of byteData first, before any decoding
    byteData.mark();
    if (byteData.remaining() >= 2) {
        int b0 = byteData.get() & 0xFF;
        int b1 = byteData.get() & 0xFF;
        // UTF-16BE BOM: 0xFE 0xFF
        if (b0 == 0xFE && b1 == 0xFF) {
            charsetName = "UTF-16BE";
            byteData.reset();
            docData = Charset.forName(charsetName).decode(byteData).toString();
            doc = parser.parseInput(docData, baseUri);
            doc.outputSettings().charset(charsetName);
            return doc;
        }
        // UTF-16LE BOM: 0xFF 0xFE
        if (b0 == 0xFF && b1 == 0xFE) {
            charsetName = "UTF-16LE";
            byteData.reset();
            docData = Charset.forName(charsetName).decode(byteData).toString();
            doc = parser.parseInput(docData, baseUri);
            doc.outputSettings().charset(charsetName);
            return doc;
        }
    }
    byteData.reset();

    if (byteData.remaining() >= 4) {
        byteData.mark();
        int b0 = byteData.get() & 0xFF;
        int b1 = byteData.get() & 0xFF;
        int b2 = byteData.get() & 0xFF;
        int b3 = byteData.get() & 0xFF;
        // UTF-32BE BOM: 0x00 0x00 0xFE 0xFF
        if (b0 == 0x00 && b1 == 0x00 && b2 == 0xFE && b3 == 0xFF) {
            charsetName = "UTF-32BE";
            byteData.reset();
            docData = Charset.forName(charsetName).decode(byteData).toString();
            doc = parser.parseInput(docData, baseUri);
            doc.outputSettings().charset(charsetName);
            return doc;
        }
        // UTF-32LE BOM: 0xFF 0xFE 0x00 0x00
        if (b0 == 0xFF && b1 == 0xFE && b2 == 0x00 && b3 == 0x00) {
            charsetName = "UTF-32LE";
            byteData.reset();
            docData = Charset.forName(charsetName).decode(byteData).toString();
            doc = parser.parseInput(docData, baseUri);
            doc.outputSettings().charset(charsetName);
            return doc;
        }
        byteData.reset();
    }

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
        docData = Charset.forName(charsetName).decode(byteData).toString();
    }
    if (docData.length() > 0 && docData.charAt(0) == UNICODE_BOM) {
        byteData.rewind();
        docData = Charset.forName(defaultCharset).decode(byteData).toString();
        docData = docData.substring(1);
        charsetName = defaultCharset;
        doc = null;
    }
    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
    }
    return doc;
}
