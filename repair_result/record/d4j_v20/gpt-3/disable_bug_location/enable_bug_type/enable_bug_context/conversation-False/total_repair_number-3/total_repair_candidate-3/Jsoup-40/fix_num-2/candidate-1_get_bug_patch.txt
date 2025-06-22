public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Accept blank or null names without throwing an exception
    if (name == null) {
        name = "";
    }
    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
