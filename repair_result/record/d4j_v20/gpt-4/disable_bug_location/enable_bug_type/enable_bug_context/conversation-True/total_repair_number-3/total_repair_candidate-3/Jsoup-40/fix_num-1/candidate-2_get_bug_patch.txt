public DocumentType(String name, String publicId, String systemId, String baseUri) {
    super(baseUri);

    // Allow blank but not null for name, publicId, systemId
    if (name == null) {
        name = "";
    }
    if (publicId == null) {
        publicId = "";
    }
    if (systemId == null) {
        systemId = "";
    }

    // Since Jsoup.parse supports blank names and characters like \u0000, 
    // sanitize control characters in name to replacement character '�' (U+FFFD)
    String sanitizedName = name.replaceAll("[\\x00-\\x1F\\x7F]", "\uFFFD");

    attr("name", sanitizedName);
    attr("publicId", publicId);
    attr("systemId", systemId);
}
