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
        // the base is unsuitable, but the attribute may be abs on its own, so try that
        try {
            URL abs = new URL(relUrl);
            return abs.toExternalForm();
        } catch (MalformedURLException e1) {
            return "";
        }
    }
}
