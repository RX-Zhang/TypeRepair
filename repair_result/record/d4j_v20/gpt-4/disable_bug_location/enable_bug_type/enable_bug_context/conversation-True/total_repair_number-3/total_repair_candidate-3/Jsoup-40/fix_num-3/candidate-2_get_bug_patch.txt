public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // If name is null or empty, sanitize to a default doctype name to avoid exceptions
    if (name == null || name.trim().isEmpty()) {
        name = "html";
    }

    // Sanitize publicId and systemId to empty strings if they are null to avoid null attributes
    if (publicId == null) {
        publicId = "";
    }
    if (systemId == null) {
        systemId = "";
    }

    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
