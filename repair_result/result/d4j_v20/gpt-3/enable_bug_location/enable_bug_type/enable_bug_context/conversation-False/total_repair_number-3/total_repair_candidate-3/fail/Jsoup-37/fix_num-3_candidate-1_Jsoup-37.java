public String html() {
    StringBuilder accum = new StringBuilder();
    outerHtmlHead(accum, 0, new Document.OutputSettings().prettyPrint(false)); // Ensure to call outerHtmlHead with appropriate parameters
    return accum.toString().trim();
}
