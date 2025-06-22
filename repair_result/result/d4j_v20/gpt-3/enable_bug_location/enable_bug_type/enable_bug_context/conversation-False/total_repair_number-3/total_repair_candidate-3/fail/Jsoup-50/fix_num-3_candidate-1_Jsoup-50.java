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

        // Detect BOM and set charsetName accordingly
        if ((b0 & 0xFF) == 0xEF && (b1 & 0xFF) == 0xBB && (b2 & 0xFF) == 0xBF) {
            charsetName = "UTF-8";
            // Advance past BOM
            byteData.position(byteData.position() + 3);
        } else if (byteData.remaining() >= 4) {
            byte b3 = byteData.get(3);
            // UTF-16BE BOM: 0xFE 0xFF
            if ((b0 & 0xFF) == 0xFE && (b1 & 0xFF) == 0xFF) {
                charsetName = "UTF-16BE";
                byteData.position(byteData.position() + 2);
            }
            // UTF-16LE BOM: 0xFF 0xFE
            else if ((b0 & 0xFF) == 0xFF && (b1 & 0xFF) == 0xFE) {
                charsetName = "UTF-16LE";
                byteData.position(byteData.position() + 2);
            }
            // UTF-32BE BOM: 00 00 FE FF
            else if ((b0 & 0xFF) == 0x00 && (b1 & 0xFF) == 0x00 && (b2 & 0xFF) == 0xFE && (b3 & 0xFF) == 0xFF) {
                charsetName = "UTF-32BE";
                byteData.position(byteData.position() + 4);
            }
            // UTF-32LE BOM: FF FE 00 00
            else if ((b0 & 0xFF) == 0xFF && (b1 & 0xFF) == 0xFE && (b2 & 0xFF) == 0x00 && (b3 & 0xFF) == 0x00) {
                charsetName = "UTF-32LE";
                byteData.position(byteData.position() + 4);
            }
        }
    }

    if (charsetName == null) { // determine from meta. safe parse as UTF-8
        docData = Charset.forName(defaultCharset).decode(byteData.asReadOnlyBuffer()).toString();
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
                // Skip BOM if present in the new decode
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
    }

    if (doc == null) {
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
    }
    return doc;
}
