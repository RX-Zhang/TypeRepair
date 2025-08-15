public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    if (name == null || name.isEmpty()) {
        attr("name", "");
    } else {
        attr("name", name);
    }
    attr("publicId", publicId);
    attr("systemId", systemId);
}
