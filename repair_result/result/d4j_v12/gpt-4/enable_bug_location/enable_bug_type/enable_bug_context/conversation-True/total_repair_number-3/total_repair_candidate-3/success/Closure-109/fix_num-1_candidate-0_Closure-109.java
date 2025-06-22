private Node parseContextTypeExpression(JsDocToken token) {
    // Instead of directly calling parseTypeName, use parseTypeExpressionAnnotation
    // to properly handle the context type expressions like (new:*)
    return parseTypeExpressionAnnotation(token);
}
