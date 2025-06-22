private static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder tb) {
    tb.insert(startTag);
    tb.tokeniser.transition(TokeniserState.Rawtext);
    tb.transition(Text);  // Removed markInsertionMode as it is unnecessary in this flow
}
