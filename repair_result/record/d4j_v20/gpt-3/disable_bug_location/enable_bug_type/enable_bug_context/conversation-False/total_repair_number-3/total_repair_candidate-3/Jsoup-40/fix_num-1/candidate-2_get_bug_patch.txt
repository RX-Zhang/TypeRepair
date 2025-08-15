public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    attr("name", name != null ? name : "");
    attr("publicId", publicId != null ? publicId : "");
    attr("systemId", systemId != null ? systemId : "");
}
