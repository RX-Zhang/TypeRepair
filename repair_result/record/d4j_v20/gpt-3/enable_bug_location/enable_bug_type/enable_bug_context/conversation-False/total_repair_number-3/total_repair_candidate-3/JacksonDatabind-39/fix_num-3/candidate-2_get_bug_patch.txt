public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // 29-Jan-2016, tatu: Simple skipping for all other tokens, but FIELD_NAME bit
    //    special unfortunately

    // Check for null input
    if (p == null || ctxt == null) {
        throw new InputValidationError("JsonParser or DeserializationContext cannot be null");
    }

    // Validate the current token
    if (p.getCurrentToken() == null) {
        throw new InputValidationError("Current token cannot be null");
    }

    p.skipChildren();
    return null;
}
