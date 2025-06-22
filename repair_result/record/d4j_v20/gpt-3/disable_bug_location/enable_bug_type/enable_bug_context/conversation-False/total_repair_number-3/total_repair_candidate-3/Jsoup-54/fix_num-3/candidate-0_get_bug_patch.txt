private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        String key = attribute.getKey();

        // Remove invalid characters from the attribute name according to XML naming rules:
        // valid xml attribute names: start with letter, underscore or colon, followed by letters, digits, hyphens, underscores, colons, periods
        // The following regex removes characters that are not allowed as per XML name rules.
        key = key.replaceAll("[^-a-zA-Z0-9_:.]", "");

        // Ensure that the first character is valid as start of an XML name (letter, underscore, colon)
        if (key.isEmpty() || !key.substring(0,1).matches("[a-zA-Z_:]")) {
            // Skip this attribute as it can't be a valid XML attribute name
            continue;
        }

        el.setAttribute(key, attribute.getValue());
    }
}
