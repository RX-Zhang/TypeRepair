public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    if (p == null) {
        return null;
    }
    // Skip the contents of the current token if it can have children (array, object)
    JsonToken currentToken = p.getCurrentToken();
    if (currentToken == JsonToken.START_OBJECT || currentToken == JsonToken.START_ARRAY) {
        p.skipChildren();
    } else if (currentToken == JsonToken.FIELD_NAME) {
        // For FIELD_NAME, we attempt skipping the value to avoid parsing errors
        p.nextToken();
        p.skipChildren();
    } else if (currentToken == null) {
        // Move to first token if parser not yet advanced
        currentToken = p.nextToken();
        if (currentToken == JsonToken.START_OBJECT || currentToken == JsonToken.START_ARRAY) {
            p.skipChildren();
        }
    }
    return null;
}
