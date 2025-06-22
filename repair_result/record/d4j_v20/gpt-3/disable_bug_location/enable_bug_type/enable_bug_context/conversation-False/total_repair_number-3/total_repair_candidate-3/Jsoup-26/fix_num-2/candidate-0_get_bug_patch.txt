public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);
    if (dirtyDocument.body() == null) {
        // If there's no body in the dirty document, return an empty clean document shell
        return Document.createShell(dirtyDocument.baseUri());
    }

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
