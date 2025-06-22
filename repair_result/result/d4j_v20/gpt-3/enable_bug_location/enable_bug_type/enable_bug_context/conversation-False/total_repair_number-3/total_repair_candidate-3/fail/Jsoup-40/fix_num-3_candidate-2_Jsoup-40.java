public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    if (name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("Name must not be empty");
    }
    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
