private Node parseContextTypeExpression(JsDocToken token) {
    if (token == JsDocToken.STAR) {
        // Handle the wildcard '*' type explicitly
        return newStringNode("*", stream.getLineno(), stream.getCharno());
    }
    return parseTypeName(token);
}
