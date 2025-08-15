public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);

    if (whitelist == null) {
        throw new IllegalStateException("Whitelist must not be null");
    }

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
