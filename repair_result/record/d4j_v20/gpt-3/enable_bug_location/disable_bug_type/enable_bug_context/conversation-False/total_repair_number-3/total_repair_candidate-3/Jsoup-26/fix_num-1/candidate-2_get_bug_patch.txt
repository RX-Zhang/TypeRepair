public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);
    Validate.notNull(whitelist);

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
