public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);

    if (dirtyDocument.body() == null) {
        // Create an empty document shell if body is null to avoid NullPointerException
        return Document.createShell(dirtyDocument.baseUri());
    }

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
