private Node parseContextTypeExpression(JsDocToken token) {
    if (token != JsDocToken.LC && token != JsDocToken.STRING) {
      return reportTypeSyntaxWarning("msg.jsdoc.expected.contextType");
    }
    return parseTypeName(token);
}
