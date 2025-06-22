private Node parseContextTypeExpression(JsDocToken token) {
    if (token == JsDocToken.STAR) {
        // Accept '*' as a valid context type expression, create a wildcard node
        int lineno = stream.getLineno();
        int charno = stream.getCharno();
        next(); // consume '*'
        return newStringNode("*", lineno, charno);
    }
    return parseTypeName(token);
}
