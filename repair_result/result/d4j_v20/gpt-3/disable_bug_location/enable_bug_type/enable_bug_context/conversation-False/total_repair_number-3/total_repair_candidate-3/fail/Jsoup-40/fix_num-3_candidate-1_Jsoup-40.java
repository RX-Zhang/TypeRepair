public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Accept blank name, but not null. Replace null with empty string.
    if (name == null) {
        name = "";
    }

    attr("name", name == null ? "" : name);
    attr("publicId", publicId == null ? "" : publicId);
    attr("systemId", systemId == null ? "" : systemId);
}
