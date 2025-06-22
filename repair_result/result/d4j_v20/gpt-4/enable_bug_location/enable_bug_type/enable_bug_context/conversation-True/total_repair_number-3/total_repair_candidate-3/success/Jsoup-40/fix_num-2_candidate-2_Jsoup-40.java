public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Allow empty name, but not null
    Validate.notNull(name);
    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
