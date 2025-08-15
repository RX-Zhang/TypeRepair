private static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder tb) {
    tb.insert(startTag);
    tb.tokeniser.transition(TokeniserState.Rawtext);
    tb.markInsertionMode();
    tb.transition(Text);
    // Ensure that the insertion mode is handled correctly after transitioning
    if (tb.currentNode().nodeName().equals("head")) {
        tb.transition(Head);
    } else {
        tb.transition(Body);
    }
}
