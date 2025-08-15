public String absUrl(String attributeKey) {
    Validate.notEmpty(attributeKey);

    if (!hasAttr(attributeKey)) {
        return ""; // nothing to make absolute with
    }

    String relUrl = attr(attributeKey);
    URL base;
    try {
        base = new URL(baseUri);
    } catch (MalformedURLException e) {
        // the base is unsuitable, but the attribute may be abs on its own, so try that
        try {
            URL abs = new URL(relUrl);
            return abs.toExternalForm();
        } catch (MalformedURLException ex) {
            return ""; // relative URL also invalid
        }
    }

    // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
    try {
        URL abs = new URL(base, relUrl);
        return abs.toExternalForm();
    } catch (MalformedURLException e) {
        return "";
    }
}
