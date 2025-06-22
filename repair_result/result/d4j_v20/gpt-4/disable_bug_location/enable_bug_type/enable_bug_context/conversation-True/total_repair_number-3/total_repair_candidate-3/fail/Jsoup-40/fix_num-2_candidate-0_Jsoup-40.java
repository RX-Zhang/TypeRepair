public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Allow blank or empty name to handle edge cases like <!DOCTYPE> without error
    if (name == null) {
        name = "";
    }
    attr("name", name);

    // Sanitize inputs for publicId and systemId, replace null with empty string
    attr("publicId", publicId == null ? "" : publicId);
    attr("systemId", systemId == null ? "" : systemId);
}
