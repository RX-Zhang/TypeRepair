private Node parseContextTypeExpression(JsDocToken token) {
    if (token == JsDocToken.STAR || token == JsDocToken.QMARK) {
        return newStringNode(stream.getString(), stream.getLineno(), stream.getCharno());
    }
    return parseTypeName(token);
}
