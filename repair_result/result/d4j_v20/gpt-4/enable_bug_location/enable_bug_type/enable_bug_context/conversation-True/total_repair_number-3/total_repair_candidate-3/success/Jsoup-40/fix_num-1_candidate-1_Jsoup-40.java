public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    if (name == null) {
        throw new IllegalArgumentException("Name must not be null");
    }
    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
