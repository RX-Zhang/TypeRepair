public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException
{
    if (_isInt) {
        visitIntFormat(visitor, typeHint, JsonParser.NumberType.BIG_INTEGER);
    } else {
        Class<?> h = handledType();
        if (h == BigDecimal.class) {
            visitFloatFormat(visitor, typeHint, JsonParser.NumberType.BIG_DECIMAL); // Corrected to BIG_DECIMAL
        } else {
            visitor.expectNumberFormat(typeHint); // Updated to ensure it calls the correct method
        }
    }
}
