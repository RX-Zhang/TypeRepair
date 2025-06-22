public String absUrl(String attributeKey) {
    Validate.notEmpty(attributeKey);

    String relUrl = attr(attributeKey);
    if (!hasAttr(attributeKey) || relUrl.isEmpty()) {
        return ""; // nothing to make absolute with
    } else {
        URL base;
        try {
            base = new URL(baseUri);
        } catch (MalformedURLException e) {
            // the base is unsuitable, but the attribute may be abs on its own, so try that
            if (!relUrl.isEmpty()) {
                try {
                    URL abs = new URL(relUrl);
                    return abs.toExternalForm();
                } catch (MalformedURLException ex) {
                    return "";
                }
            }
            return "";
        }
        // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
        URL abs = new URL(base, relUrl);
        return abs.toExternalForm();
    }
}
