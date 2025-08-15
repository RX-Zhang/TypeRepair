public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // Validate input before processing
    if (p == null || ctxt == null) {
        throw new IllegalArgumentException("JsonParser and DeserializationContext cannot be null");
    }

    // 29-Jan-2016, tatu: Simple skipping for all other tokens, but FIELD_NAME bit
    //    special unfortunately
    p.skipChildren();
    return null;
}
