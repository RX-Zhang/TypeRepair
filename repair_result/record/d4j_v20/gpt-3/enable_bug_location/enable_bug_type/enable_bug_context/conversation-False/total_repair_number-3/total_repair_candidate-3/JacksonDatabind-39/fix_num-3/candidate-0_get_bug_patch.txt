public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    // 29-Jan-2016, tatu: Simple skipping for all other tokens, but FIELD_NAME bit
    //    special unfortunately
    if (p.getCurrentToken() == JsonToken.FIELD_NAME) {
        String fieldName = p.getCurrentName();
        // Implement input validation for known fields
        if (!isValidField(fieldName)) {
            throw new IOException("Unrecognized field: " + fieldName);
        }
    }
    p.skipChildren();
    return null;
}

private boolean isValidField(String fieldName) {
    // Add logic to check if the fieldName is one of the known properties
    return "version".equals(fieldName) || "application".equals(fieldName) || 
           "item".equals(fieldName) || "item2".equals(fieldName);
}
