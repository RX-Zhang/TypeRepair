private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // Clean key by removing invalid XML characters for attribute names
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]*
        String originalKey = attribute.getKey();
        StringBuilder keyBuilder = new StringBuilder();

        // The first character must be a letter, underscore, or colon
        if (originalKey.length() > 0) {
            char first = originalKey.charAt(0);
            if ( (first >= 'A' && first <= 'Z') 
                 || (first >= 'a' && first <= 'z') 
                 || first == '_' 
                 || first == ':') {
                keyBuilder.append(first);
            } else {
                // Skip this attribute if the first char is invalid
                continue;
            }
        } else {
            continue;
        }

        // Remaining characters can be letters, digits, hyphen, underscore, colon, or dot
        for (int i = 1; i < originalKey.length(); i++) {
            char c = originalKey.charAt(i);
            if ( (c >= 'A' && c <= 'Z')
                 || (c >= 'a' && c <= 'z')
                 || (c >= '0' && c <= '9')
                 || c == '-'
                 || c == '_'
                 || c == ':'
                 || c == '.') {
                keyBuilder.append(c);
            }
            // else skip invalid characters
        }

        if (keyBuilder.length() > 0) {
            el.setAttribute(keyBuilder.toString(), attribute.getValue());
        }
    }
}
