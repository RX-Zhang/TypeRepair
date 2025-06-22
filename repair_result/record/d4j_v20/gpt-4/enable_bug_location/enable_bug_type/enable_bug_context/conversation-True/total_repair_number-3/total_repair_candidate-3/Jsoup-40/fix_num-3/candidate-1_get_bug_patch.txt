public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Allow empty name, but convert null to empty string to avoid NPE
    if (name == null) {
        name = "";
    }
    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
