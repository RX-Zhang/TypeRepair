private Node parseContextTypeExpression(JsDocToken token) {
    if (token == JsDocToken.STAR) {
        next();
        return newStringNode("*", stream.getLineno(), stream.getCharno());
    }
    return parseTypeName(token);
}
