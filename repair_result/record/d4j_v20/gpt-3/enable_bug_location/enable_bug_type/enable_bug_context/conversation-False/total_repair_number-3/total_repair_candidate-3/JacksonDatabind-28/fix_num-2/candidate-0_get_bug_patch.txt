public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
{
    switch (p.getCurrentToken()) {
        case START_OBJECT:
            // advance to first field or END_OBJECT
            p.nextToken();
            return deserializeObject(p, ctxt, ctxt.getNodeFactory());
        case FIELD_NAME:
            return deserializeObject(p, ctxt, ctxt.getNodeFactory());
        case END_OBJECT:
            // empty ObjectNode
            return ctxt.getNodeFactory().objectNode();
        default:
            throw ctxt.mappingException(ObjectNode.class);
    }
}
