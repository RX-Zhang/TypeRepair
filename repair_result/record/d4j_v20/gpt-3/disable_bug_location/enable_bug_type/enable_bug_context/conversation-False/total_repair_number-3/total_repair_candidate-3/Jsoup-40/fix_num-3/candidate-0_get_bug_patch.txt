public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Allow blank names but not null, replace empty names with empty string
    if (name == null) {
        name = "";
    }
    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
