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
        } catch (MalformedURLException e1) {
            return "";
        }
    }

    try {
        // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
        if (relUrl.startsWith("?")) {
            // Need to manually merge query parameters preserving the path
            String baseStr = base.toExternalForm();
            int queryIndex = baseStr.indexOf('?');
            if (queryIndex > -1) {
                baseStr = baseStr.substring(0, queryIndex);
            }
            return baseStr + relUrl;
        } else {
            URL abs = new URL(base, relUrl);
            return abs.toExternalForm();
        }
    } catch (MalformedURLException e) {
        return "";
    }
}
