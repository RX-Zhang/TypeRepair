private JavaType _mapType(Class<?> rawClass)
{
    // 28-May-2015, tatu: Properties are special, as per [databind#810]
    JavaType[] typeParams = findTypeParameters(rawClass, Map.class);
    // ok to have no types ("raw")
    if (typeParams == null) {
        // Special handling for Properties, to force key and value types to String
        if (Properties.class.isAssignableFrom(rawClass)) {
            return MapType.construct(rawClass,
                CORE_TYPE_STRING,
                CORE_TYPE_STRING);
        }
        return MapType.construct(rawClass, _unknownType(), _unknownType());
    }
    // but exactly 2 types if any found
    if (typeParams.length != 2) {
        throw new IllegalArgumentException("Strange Map type "+rawClass.getName()+": can not determine type parameters");
    }
    // Special case: When rawClass is Properties, override value type to String forcibly to ensure proper behavior
    if (Properties.class.isAssignableFrom(rawClass)) {
        return MapType.construct(rawClass,
            typeParams[0],
            CORE_TYPE_STRING);
    }
    return MapType.construct(rawClass, typeParams[0], typeParams[1]);
}
