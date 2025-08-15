public String html() {
    StringBuilder accum = new StringBuilder();
    outerHtmlHead(accum, 0, new Document.OutputSettings().prettyPrint(false));
    return accum.toString().trim();
}
