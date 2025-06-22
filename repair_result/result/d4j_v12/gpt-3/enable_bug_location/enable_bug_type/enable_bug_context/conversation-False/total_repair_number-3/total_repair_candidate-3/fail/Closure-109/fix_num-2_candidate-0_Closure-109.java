private Node parseContextTypeExpression(JsDocToken token) {
    if (token == JsDocToken.STAR) {
        return newStringNode("*", stream.getLineno(), stream.getCharno());
    }
    return parseTypeName(token);
}
