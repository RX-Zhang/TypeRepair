public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);

    if (dirtyDocument.body() == null) {
        throw new RuntimeException("The dirty document does not have a body.");
    }

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
