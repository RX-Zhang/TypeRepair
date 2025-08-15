static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // look for BOM - overrides any other header or input
    byteData.mark();
    Charset detectedCharset = null;

    // Check for BOM at the start of byte data
    if (byteData.remaining() >= 4) {
        byte b0 = byteData.get(0);
        byte b1 = byteData.get(1);
        byte b2 = byteData.get(2);
        byte b3 = byteData.get(3);

        if ((b0 == (byte)0x00 && b1 == (byte)0x00 && b2 == (byte)0xFE && b3 == (byte)0xFF)) { // UTF-32BE BOM
            detectedCharset = Charset.forName("UTF-32BE");
            byteData.position(4);
        } else if ((b0 == (byte)0xFF && b1 == (byte)0xFE && b2 == (byte)0x00 && b3 == (byte)0x00)) { // UTF-32LE BOM
            detectedCharset = Charset.forName("UTF-32LE");
            byteData.position(4);
        }
    }
    if (detectedCharset == null && byteData.remaining() >= 3) {
        byte b0 = byteData.get(0);
        byte b1 = byteData.get(1);
        byte b2 = byteData.get(2);

        if (b0 == (byte)0xEF && b1 == (byte)0xBB && b2 == (byte)0xBF) { // UTF-8 BOM
            detectedCharset = Charset.forName("UTF-8");
            byteData.position(3);
        }
    }
    if (detectedCharset == null && byteData.remaining() >= 2) {
        byte b0 = byteData.get(0);
        byte b1 = byteData.get(1);

        if (b0 == (byte)0xFE && b1 == (byte)0xFF) { // UTF-16BE BOM
            detectedCharset = Charset.forName("UTF-16BE");
            byteData.position(2);
        } else if (b0 == (byte)0xFF && b1 == (byte)0xFE) { // UTF-16LE BOM
            detectedCharset = Charset.forName("UTF-16LE");
            byteData.position(2);
        }
    }

    if (detectedCharset != null) {
        charsetName = detectedCharset.name();
        // Decode from after BOM
        docData = detectedCharset.decode(byteData.slice()).toString();
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
        return doc;
    }

    byteData.reset();

    if (charsetName == null) { // determine from meta. safe parse as UTF-8
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

    // Check for BOM character at start of docData and remove if needed
    if (docData.length() > 0 && docData.charAt(0) == UNICODE_BOM) {
        // The BOM character is present but no BOM detected in byte data above,
        // so just remove BOM char from string
        docData = docData.substring(1);
        charsetName = charsetName == null ? defaultCharset : charsetName;
        doc = null;
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
    }
    return doc;
}
