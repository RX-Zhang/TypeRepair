public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    if (name == null) {
        name = "";
    }
    // Sanitize name: replace control characters with replacement character or remove them
    name = name.replaceAll("[\\x00-\\x1F\\x7F]", "\uFFFD");

    attr("name", name == null ? "" : name);
    attr("publicId", publicId == null ? "" : publicId);
    attr("systemId", systemId == null ? "" : systemId);
}
