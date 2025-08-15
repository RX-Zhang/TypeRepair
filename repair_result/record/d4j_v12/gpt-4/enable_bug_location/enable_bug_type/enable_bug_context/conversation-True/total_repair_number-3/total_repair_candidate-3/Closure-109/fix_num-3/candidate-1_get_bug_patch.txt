private Node parseContextTypeExpression(JsDocToken token) {
    if (token == JsDocToken.FUNCTION) {
        return parseFunctionType(token);
    }
    return parseTypeName(token);
}
