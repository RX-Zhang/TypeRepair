public String absUrl(String attributeKey) {
    Validate.notEmpty(attributeKey);

    if (!hasAttr(attributeKey)) {
        return ""; // nothing to make absolute with
    }

    String relUrl = attr(attributeKey);
    try {
        URL base;
        try {
            base = new URL(baseUri);
        } catch (MalformedURLException e) {
            // the base is unsuitable, but the attribute may be abs on its own, so try that
            URL abs = new URL(relUrl);
            return abs.toExternalForm();
        }

        // Special handling to preserve the original file/path when resolving query parameters, 
        // because java.net.URL incorrectly replaces the file with '?' part.
        if (relUrl.startsWith("?") && base.getFile() != null && !base.getFile().isEmpty()) {
            String file = base.getFile();
            String protocol = base.getProtocol();
            String host = base.getHost();
            int port = base.getPort();
            String ref = base.getRef();

            StringBuilder sb = new StringBuilder();
            sb.append(protocol);
            sb.append("://");
            sb.append(host);
            if (port != -1) {
                sb.append(':').append(port);
            }
            sb.append(file);
            sb.append(relUrl); // append query directly after file

            if (ref != null) {
                sb.append('#').append(ref);
            }
            return sb.toString();
        }

        URL abs = new URL(base, relUrl);
        return abs.toExternalForm();
    } catch (MalformedURLException e) {
        return "";
    }
}
