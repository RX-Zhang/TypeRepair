public String absUrl(String attributeKey) {
    Validate.notEmpty(attributeKey);

    if (!hasAttr(attributeKey)) {
        return ""; // nothing to make absolute with
    }

    String relUrl = attr(attributeKey);

    try {
        URL base = new URL(baseUri());
        // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
        URL abs = new URL(base, relUrl);
        return abs.toExternalForm();
    } catch (MalformedURLException e) {
        try {
            // the base is unsuitable, but the attribute may be absolute on its own
            URL abs = new URL(relUrl);
            return abs.toExternalForm();
        } catch (MalformedURLException ignored) {
            return "";
        }
    }
}
