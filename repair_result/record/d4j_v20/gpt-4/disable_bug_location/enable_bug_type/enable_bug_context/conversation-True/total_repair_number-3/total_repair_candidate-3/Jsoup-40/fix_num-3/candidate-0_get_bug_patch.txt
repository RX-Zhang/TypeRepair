public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Allow name to be blank but not null
    if (name == null) {
        throw new IllegalArgumentException("Name must not be null");
    }
    attr("name", name);

    // For publicId and systemId, allow empty strings but replace null with empty string
    attr("publicId", publicId != null ? publicId : "");
    attr("systemId", systemId != null ? systemId : "");
}
