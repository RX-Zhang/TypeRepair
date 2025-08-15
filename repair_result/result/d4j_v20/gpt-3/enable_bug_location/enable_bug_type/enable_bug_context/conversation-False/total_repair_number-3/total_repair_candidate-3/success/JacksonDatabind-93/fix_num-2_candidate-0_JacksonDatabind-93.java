public void validateSubType(DeserializationContext ctxt, JavaType type) throws JsonMappingException
{
    // There are certain nasty classes that could cause problems, mostly
    // via default typing -- catch them here.
    if (type == null) {
        throw JsonMappingException.from(ctxt, "Null JavaType passed for validation");
    }
    final Class<?> raw = type.getRawClass();
    if (raw == null) {
        throw JsonMappingException.from(ctxt, "JavaType has null raw class");
    }
    String full = raw.getName();

    main_check:
    do {
        if (_cfgIllegalClassNames != null && _cfgIllegalClassNames.contains(full)) {
            break;
        }

        // 18-Dec-2017, tatu: As per [databind#1855], need bit more sophisticated handling
        //    for some Spring framework types
        // 05-Jan-2017, tatu: ... also, only applies to classes, not interfaces
        if (full != null && full.startsWith(PREFIX_STRING)) {
            for (Class<?> cls = raw; cls != null && cls != Object.class; cls = cls.getSuperclass()) {
                String name = cls.getSimpleName();
                if ("AbstractPointcutAdvisor".equals(name)
                        || "AbstractApplicationContext".equals(name)) {
                    break main_check;
                }
            }
        }
        return;
    } while (false);

    throw JsonMappingException.from(ctxt,
            String.format("Illegal type (%s) to deserialize: prevented for security reasons", full));
}
