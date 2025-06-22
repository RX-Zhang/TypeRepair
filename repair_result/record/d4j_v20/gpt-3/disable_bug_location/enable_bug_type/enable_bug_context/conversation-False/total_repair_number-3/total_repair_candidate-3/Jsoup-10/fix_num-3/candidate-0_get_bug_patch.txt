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
            return "";
        }
    }

    try {
        // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
        if (relUrl.startsWith("?")) {
            // append the query to the file portion of the base URI
            String baseStr = base.toExternalForm();
            int queryStart = baseStr.indexOf("?");
            if (queryStart != -1) {
                baseStr = baseStr.substring(0, queryStart);
            }
            return baseStr + relUrl;
        }
        URL abs = new URL(base, relUrl);
        return abs.toExternalForm();
    } catch (MalformedURLException e) {
        return "";
    }
}
