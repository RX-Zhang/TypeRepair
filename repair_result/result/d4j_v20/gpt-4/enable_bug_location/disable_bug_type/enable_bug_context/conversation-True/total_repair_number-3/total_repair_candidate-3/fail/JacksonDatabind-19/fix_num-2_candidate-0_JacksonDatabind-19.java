private JavaType _mapType(Class<?> rawClass)
{
    // 28-May-2015, tatu: Properties are special, as per [databind#810]
    JavaType[] typeParams = findTypeParameters(rawClass, Map.class);
    // ok to have no types ("raw")
    if (typeParams == null) {
        return MapType.construct(rawClass, _unknownType(), _unknownType());
    }
    // but exactly 2 types if any found
    if (typeParams.length != 2) {
        throw new IllegalArgumentException("Strange Map type " + rawClass.getName() + ": can not determine type parameters");
    }
    
    // Ensure that the types are not null to avoid potential NullPointerException
    if (typeParams[0] == null || typeParams[1] == null) {
        throw new IllegalArgumentException("Type parameters for Map type " + rawClass.getName() + " cannot be null");
    }
    
    return MapType.construct(rawClass, typeParams[0], typeParams[1]);
}
