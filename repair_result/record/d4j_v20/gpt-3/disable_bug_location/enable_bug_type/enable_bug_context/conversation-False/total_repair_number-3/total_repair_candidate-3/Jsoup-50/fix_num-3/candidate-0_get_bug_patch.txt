static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // look for BOM - overrides any other header or input
    if (charsetName == null) {
        // Try to detect BOM first
        byteData.mark();
        if (byteData.remaining() >= 3) {
            byte b1 = byteData.get();
            byte b2 = byteData.get();
            byte b3 = byteData.get();
            byteData.reset();

            // UTF-8 BOM
            if ((b1 & 0xFF) == 0xEF && (b2 & 0xFF) == 0xBB && (b3 & 0xFF) == 0xBF) {
                charsetName = "UTF-8";
                byteData.position(byteData.position() + 3); // skip BOM
            } 
            // UTF-16BE BOM
            else if ((b1 & 0xFF) == 0xFE && (b2 & 0xFF) == 0xFF) {
                charsetName = "UTF-16BE";
                byteData.position(byteData.position() + 2);
            } 
            // UTF-16LE BOM
            else if ((b1 & 0xFF) == 0xFF && (b2 & 0xFF) == 0xFE) {
                charsetName = "UTF-16LE";
                byteData.position(byteData.position() + 2);
            }
            // UTF-32BE BOM
            else if (byteData.remaining() >= 4) {
                byte b4 = byteData.get(3);
                if ((b1 & 0xFF) == 0x00 && (b2 & 0xFF) == 0x00 && (b3 & 0xFF) == 0xFE && (b4 & 0xFF) == 0xFF) {
                    charsetName = "UTF-32BE";
                    byteData.position(byteData.position() + 4);
                } 
                // UTF-32LE BOM
                else if ((b1 & 0xFF) == 0xFF && (b2 & 0xFF) == 0xFE && (b3 & 0xFF) == 0x00 && (b4 & 0xFF) == 0x00) {
                    charsetName = "UTF-32LE";
                    byteData.position(byteData.position() + 4);
                }
            }
        }
    }

    if (charsetName == null) { // determine from meta. safe parse as UTF-8
        charsetName = defaultCharset;
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

    if (docData.length() > 0 && docData.charAt(0) == UNICODE_BOM) {
        // Strip BOM char from the string
        docData = docData.substring(1);
        doc = null;
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
    }
    return doc;
}
