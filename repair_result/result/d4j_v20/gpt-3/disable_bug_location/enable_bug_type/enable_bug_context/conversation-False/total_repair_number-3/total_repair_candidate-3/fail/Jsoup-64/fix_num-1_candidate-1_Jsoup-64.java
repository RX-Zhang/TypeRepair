private static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder tb) {
    tb.insert(startTag);
    tb.tokeniser.transition(TokeniserState.Rawtext);
    tb.markInsertionMode();
    tb.transition(Text); // Ensure that the transition is to the correct state after handling raw text
}
