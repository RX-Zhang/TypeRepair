public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);

    if (dirtyDocument.body() == null) {
        throw new RuntimeException("Dirty document body cannot be null.");
    }

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
