static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
    String docData;
    Document doc = null;

    // Detect BOM and override charset if BOM present
    // Mark the position so we can rewind after reading BOM
    byteData.mark();
    byte[] bom = new byte[4];
    int bomLength = 0;
    int remaining = byteData.remaining();

    if (remaining >= 4) {
        bom[0] = byteData.get();
        bom[1] = byteData.get();
        bom[2] = byteData.get();
        bom[3] = byteData.get();
        bomLength = 4;
    } else if (remaining > 0) {
        for (int i = 0; i < remaining; i++) {
            bom[i] = byteData.get();
        }
        bomLength = remaining;
    }

    String bomCharset = null;
    int skip = 0;

    if (bomLength >= 3 &&
        (bom[0] & 0xFF) == 0xEF &&
        (bom[1] & 0xFF) == 0xBB &&
        (bom[2] & 0xFF) == 0xBF) {
        bomCharset = "UTF-8";
        skip = 3;
    } else if (bomLength >= 4 &&
        (bom[0] & 0xFF) == 0x00 &&
        (bom[1] & 0xFF) == 0x00 &&
        (bom[2] & 0xFF) == 0xFE &&
        (bom[3] & 0xFF) == 0xFF) {
        bomCharset = "UTF-32BE";
        skip = 4;
    } else if (bomLength >= 4 &&
        (bom[0] & 0xFF) == 0xFF &&
        (bom[1] & 0xFF) == 0xFE &&
        (bom[2] & 0xFF) == 0x00 &&
        (bom[3] & 0xFF) == 0x00) {
        bomCharset = "UTF-32LE";
        skip = 4;
    } else if (bomLength >= 2 &&
        (bom[0] & 0xFF) == 0xFE &&
        (bom[1] & 0xFF) == 0xFF) {
        bomCharset = "-16BE";
        skip = 2;
    } else if (bomLength >= 2 &&
        (bom[0] & 0xFF) == 0xFF &&
        (bom[1] & 0xFF) == 0xFE) {
        bomCharset = "UTF-16LE";
        skip = 2;
    }

    // Reset position to after BOM if BOM detected; else reset to start
    byteData.reset();
    byteData.position(byteData.position() + skip);

    if (bomCharset != null) {
        charsetName = bomCharset;
    }

    if (charsetName == null) { // determine from meta. safe parse as UTF-8
        // read as UTF-8 or defaultCharset if UTF-8 not available
        docData = Charset.forName(defaultCharset).decode(byteData.slice()).toString();
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

            if (foundCharset != null && foundCharset.length() != 0 && !foundCharset.equalsIgnoreCase(defaultCharset)) {
                foundCharset = foundCharset.trim().replaceAll("[\"']", "");
                charsetName = foundCharset;
                byteData.position(0);
                docData = Charset.forName(foundCharset).decode(byteData).toString();
                doc = null;
            }
        }
    } else { // specified by content type header (or by user on file load)
        Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
        byteData.position(skip); // reset position after BOM if any
        docData = Charset.forName(charsetName).decode(byteData.slice()).toString();
    }

    if (docData.length() > 0 && docData.charAt(0) == UNICODE_BOM) {
        docData = docData.substring(1);
        if (charsetName == null) {
            charsetName = defaultCharset;
        }
        doc = null;
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        if (charsetName != null) {
            doc.outputSettings().charset(charsetName);
        }
    }
    return doc;
}
