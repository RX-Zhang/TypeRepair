public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    try {
        // Skip the children tokens to ignore the current subtree
        p.skipChildren();
    } catch (Exception e) {
        // Optionally, log or handle if needed; swallowing to ensure null return
    }
    return null;
}
