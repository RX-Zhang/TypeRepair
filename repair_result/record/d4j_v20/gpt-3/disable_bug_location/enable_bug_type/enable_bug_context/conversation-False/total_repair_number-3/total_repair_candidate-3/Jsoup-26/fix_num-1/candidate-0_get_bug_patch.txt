public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);

    Document clean = Document.createShell(dirtyDocument.baseUri());
    Element dirtyBody = dirtyDocument.body();
    Element cleanBody = clean.body();

    if (dirtyBody != null && cleanBody != null) {
        copySafeNodes(dirtyBody, cleanBody);
    }

    return clean;
}
