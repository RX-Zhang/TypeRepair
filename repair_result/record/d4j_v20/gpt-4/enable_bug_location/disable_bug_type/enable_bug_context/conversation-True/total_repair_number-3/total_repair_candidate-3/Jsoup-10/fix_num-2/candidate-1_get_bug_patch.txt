public String absUrl(String attributeKey) {
    Validate.notEmpty(attributeKey);

    if (!hasAttr(attributeKey)) {
        return ""; // nothing to make absolute with
    }

    String relUrl = attr(attributeKey);
    URL base;
    try {
        try {
            base = new URL(baseUri());
        } catch (MalformedURLException e) {
            // the base is unsuitable, but the attribute may be abs on its own, so try that
            URL abs = new URL(relUrl);
            return abs.toExternalForm();
        }

        // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
        if (relUrl.startsWith("?") && base.getPath() != null && !base.getPath().isEmpty()) {
            String path = base.getPath();
            if (!path.endsWith("/")) {
                path = path.substring(0, path.lastIndexOf('/') + 1);
            }
            URL abs = new URL(base.getProtocol(), base.getHost(), base.getPort(), path + relUrl);
            return abs.toExternalForm();
        }
        
        URL abs = new URL(base, relUrl);
        return abs.toExternalForm();
    } catch (MalformedURLException e) {
        return "";
    }
}
