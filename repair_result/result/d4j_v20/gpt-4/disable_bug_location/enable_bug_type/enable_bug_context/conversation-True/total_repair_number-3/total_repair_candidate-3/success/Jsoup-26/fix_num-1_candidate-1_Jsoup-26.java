public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);

    if (dirtyDocument.body() == null) {
        // If there is no body element, return an empty shell Document
        return Document.createShell(dirtyDocument.baseUri());
    }

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
