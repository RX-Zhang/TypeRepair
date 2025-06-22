public String absUrl(String attributeKey) {
    Validate.notEmpty(attributeKey);

    if (!hasAttr(attributeKey)) {
        return ""; // nothing to make absolute with
    }

    String relUrl = attr(attributeKey);
    try {
        URL base = new URL(baseUri);
        // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
        if (relUrl.startsWith("?") && base.getFile() != null && !base.getFile().isEmpty()) {
            // construct string manually to preserve base file with query
            String baseWithoutQuery = base.toExternalForm().split("\\?")[0];
            return baseWithoutQuery + relUrl;
        }
        URL abs = new URL(base, relUrl);
        return abs.toExternalForm();
    } catch (MalformedURLException e) {
        try {
            // if baseUri is malformed, maybe relUrl is abs
            URL abs = new URL(relUrl);
            return abs.toExternalForm();
        } catch (MalformedURLException ex) {
            return "";
        }
    }
}
