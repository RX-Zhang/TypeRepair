public String absUrl(String attributeKey) {
    Validate.notEmpty(attributeKey);

    if (!hasAttr(attributeKey)) {
        return ""; // nothing to make absolute with
    }

    String relUrl = attr(attributeKey);
    try {
        URL base = new URL(baseUri);
        // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
        // To fix this, if relUrl starts with '?', append to the path of base URL.
        if (relUrl.startsWith("?")) {
            String baseUrlWithoutQuery = baseUri;
            int queryIndex = baseUri.indexOf('?');
            if (queryIndex != -1) {
                baseUrlWithoutQuery = baseUri.substring(0, queryIndex);
            }
            URL fixedBase = new URL(baseUrlWithoutQuery);
            URL abs = new URL(fixedBase.toExternalForm() + relUrl);
            return abs.toExternalForm();
        } else {
            URL abs = new URL(base, relUrl);
            return abs.toExternalForm();
        }
    } catch (MalformedURLException e) {
        try {
            // the base is unsuitable, but the attribute may be abs on its own, so try that
            URL abs = new URL(relUrl);
            return abs.toExternalForm();
        } catch (MalformedURLException e1) {
            return "";
        }
    }
}
