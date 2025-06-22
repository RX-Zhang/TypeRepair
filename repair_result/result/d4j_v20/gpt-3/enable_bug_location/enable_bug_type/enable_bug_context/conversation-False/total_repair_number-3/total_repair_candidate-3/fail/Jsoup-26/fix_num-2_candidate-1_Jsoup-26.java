public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);

    // Check if the body of the dirty document is null to prevent NullPointerException
    if (dirtyDocument.body() == null) {
        throw new RuntimeException("The dirty document does not contain a body.");
    }

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
