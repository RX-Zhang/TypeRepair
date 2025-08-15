public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Allow empty or null names (sanitize null to empty string)
    if (name == null) {
        name = "";
    }
    attr("name", name);
    attr("publicId", publicId != null ? publicId : "");
    attr("systemId", systemId != null ? systemId : "");
}
