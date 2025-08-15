public String html() {
    StringBuilder accum = new StringBuilder();
    outerHtmlHead(accum, 0, new Document.OutputSettings().prettyPrint(false)); // Ensure prettyPrint is set to false
    return accum.toString().trim();
}
