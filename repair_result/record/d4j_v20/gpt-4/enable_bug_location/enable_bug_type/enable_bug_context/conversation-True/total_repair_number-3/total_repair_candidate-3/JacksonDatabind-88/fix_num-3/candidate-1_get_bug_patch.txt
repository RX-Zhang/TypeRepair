protected JavaType _typeFromId(String id, DatabindContext ctxt) throws IOException
{
    /* 30-Jan-2010, tatu: Most ids are basic class names; so let's first
     *    check if any generics info is added; and only then ask factory
     *    to do translation when necessary
     */
    TypeFactory tf = ctxt.getTypeFactory();
    if (id.indexOf('<') > 0) {
        JavaType t = tf.constructFromCanonical(id);
        // 17-Aug-2017, tatu: As per [databind#1735] need to ensure assignment
        // compatibility -- needed later anyway, and not doing so may open
        // security issues.
        if (!_baseType.getRawClass().isAssignableFrom(t.getRawClass())) {
            throw new JsonMappingException(ctxt.getParser(),
                    "Invalid type id '" + id + "': specified class is not subtype of base type");
        }
        return t;
    }
    Class<?> cls;
    try {
        cls =  tf.findClass(id);
    } catch (ClassNotFoundException e) {
        if (ctxt instanceof DeserializationContext) {
            DeserializationContext dctxt = (DeserializationContext) ctxt;
            return dctxt.handleUnknownTypeId(_baseType, id, this, "no such class found");
        }
        return null;
    } catch (Exception e) {
        throw new IllegalArgumentException("Invalid type id '"+id+"' (for id type 'Id.class'): "+e.getMessage(), e);
    }
    if (!_baseType.getRawClass().isAssignableFrom(cls)) {
        throw new JsonMappingException(ctxt.getParser(),
                "Invalid type id '" + id + "': specified class '" + cls.getName() + "' is not subtype of base type");
    }
    return tf.constructSpecializedType(_baseType, cls);
}
