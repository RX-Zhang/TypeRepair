public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Allow empty name but replace null with empty string
    name = name == null ? "" : name;
    publicId = publicId == null ? "" : publicId;
    systemId = systemId == null ? "" : systemId;

    // Sanitize name: if empty, replace with a placeholder to avoid IllegalArgumentException
    if (name.isEmpty()) {
        name = "doctype"; // or any default valid name
    }

    attr("name", name);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
