public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Accept blank or null name, sanitize input to avoid invalid characters:
    if (name == null) {
        name = "";
    } else {
        // Replace null characters or other invalid chars with replacement char
        name = name.replace('\u0000', '\uFFFD');
    }
    attr("name", name);

    if (publicId == null) publicId = "";
    if (systemId == null) systemId = "";
    attr("publicId", publicId);
    attr("systemId", systemId);
}
