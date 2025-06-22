static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
    checkArgument(supertype.isAssignableFrom(contextRawType));
    Type genericSupertype = $Gson$Types.getGenericSupertype(context, contextRawType, supertype);
    return $Gson$Types.resolve(context, contextRawType, genericSupertype);
}
