static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // look for BOM - overrides any other header or input
    byteData.mark();
    if (byteData.remaining() >= 3) {
        byte b0 = byteData.get();
        byte b1 = byteData.get();
        byte b2 = byteData.get();
        byteData.reset();

        // UTF-8 BOM  EF BB BF
        if ((b0 & 0xFF) == 0xEF && (b1 & 0xFF) == 0xBB && (b2 & 0xFF) == 0xBF) {
            // skip BOM bytes
            byteData.position(byteData.position() + 3);
            charsetName = "UTF-8";
        } else if (byteData.remaining() >= 2) {
            // Check UTF-16 BOM
            byteData.mark();
            b0 = byteData.get();
            b1 = byteData.get();
            byteData.reset();

            if ((b0 & 0xFF) == 0xFE && (b1 & 0xFF) == 0xFF) { // UTF-16BE
                charsetName = "UTF-16BE";
            } else if ((b0 & 0xFF) == 0xFF && (b1 & 0xFF) == 0xFE) { // UTF-16LE
                charsetName = "UTF-16LE";
            }
            // Could add UTF-32 BOM detection here if desired
        }
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
