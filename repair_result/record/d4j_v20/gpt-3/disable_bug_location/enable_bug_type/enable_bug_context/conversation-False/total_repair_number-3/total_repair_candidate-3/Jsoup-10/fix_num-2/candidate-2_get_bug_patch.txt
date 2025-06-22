public String absUrl(String attributeKey) {
    Validate.notEmpty(attributeKey);

    if (!hasAttr(attributeKey)) {
        return ""; // nothing to make absolute with
    }

    String relUrl = attr(attributeKey);
    try {
        URL base = new URL(baseUri);

        // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
        if (relUrl.startsWith("?")) {
            // Append query to base path directly, preserving the last segment of the path
            String basePath = base.getPath();
            if (basePath == null || basePath.isEmpty())
                basePath = "/";
            URL fixed = new URL(base.getProtocol(), base.getHost(), base.getPort(), basePath + relUrl);
            return fixed.toExternalForm();
        }

        URL abs = new URL(base, relUrl);
        return abs.toExternalForm();
    } catch (MalformedURLException e) {
        try {
            // the base is unsuitable, but the attribute may be absolute URL on its own, so try that
            URL abs = new URL(relUrl);
            return abs.toExternalForm();
        } catch (MalformedURLException e1) {
            return "";
        }
    }
}
