public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Implement input validation and sanitization
    if (name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("Name must not be empty");
    }
    if (publicId == null) {
        publicId = ""; // Default to empty string if null
    }
    if (systemId == null) {
        systemId = ""; // Default to empty string if null
    }

    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
