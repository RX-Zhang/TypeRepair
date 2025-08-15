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
        } catch (MalformedURLException e2) {
            return "";
        }
    }

    // workaround to correctly handle relative URLs starting with '?'
    if (relUrl.startsWith("?")) {
        // append the query to the path component of base URI
        String baseStr = base.toExternalForm();
        int queryIndex = baseStr.indexOf("?");
        String baseWithoutQuery = (queryIndex != -1) ? baseStr.substring(0, queryIndex) : baseStr;
        return baseWithoutQuery + relUrl;
    }

    try {
        URL abs = new URL(base, relUrl);
        return abs.toExternalForm();
    } catch (MalformedURLException e) {
        return "";
    }
}
