public Document clean(Document dirtyDocument) {
    Validate.notNull(dirtyDocument);
    
    // Check if the body of the document is null to prevent NullPointerException
    if (dirtyDocument.body() == null) {
        throw new IllegalArgumentException("The dirty document must have a body.");
    }

    Document clean = Document.createShell(dirtyDocument.baseUri());
    copySafeNodes(dirtyDocument.body(), clean.body());

    return clean;
}
