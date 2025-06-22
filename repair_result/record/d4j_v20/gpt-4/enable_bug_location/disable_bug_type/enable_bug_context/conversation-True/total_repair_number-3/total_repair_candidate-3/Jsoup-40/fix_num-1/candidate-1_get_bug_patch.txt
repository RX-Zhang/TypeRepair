public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Implementing input validation and sanitization
    if (name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("Document type name must not be empty");
    }
    if (publicId != null) {
        publicId = publicId.trim();
    }
    if (systemId != null) {
        systemId = systemId.trim();
    }

    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
