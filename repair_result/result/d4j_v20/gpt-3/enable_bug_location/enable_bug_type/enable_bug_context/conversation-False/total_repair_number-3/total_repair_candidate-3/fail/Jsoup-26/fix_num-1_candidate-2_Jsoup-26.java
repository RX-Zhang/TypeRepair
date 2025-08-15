public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);

    // Check if the body of the dirty document is null to prevent NullPointerException
    if (dirtyDocument.body() == null) {
        throw new RuntimeException("Document body is null.");
    }

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
