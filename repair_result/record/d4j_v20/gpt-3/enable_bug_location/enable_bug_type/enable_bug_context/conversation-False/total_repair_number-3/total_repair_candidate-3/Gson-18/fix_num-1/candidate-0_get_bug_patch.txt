static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
    // wildcards are useless for resolving supertypes. As the upper bound has the same raw type, use it instead
    checkArgument(supertype.isAssignableFrom(contextRawType));
    Type resolvedType = $Gson$Types.getGenericSupertype(context, contextRawType, supertype);
    if (resolvedType instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) resolvedType;
        return parameterizedType.getRawType();
    }
    return resolvedType;
}
