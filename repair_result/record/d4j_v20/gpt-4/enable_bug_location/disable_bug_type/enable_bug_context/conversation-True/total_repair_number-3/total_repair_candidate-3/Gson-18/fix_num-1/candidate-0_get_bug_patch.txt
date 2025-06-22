static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
    // wildcards are useless for resolving supertypes. As the upper bound has the same raw type, use it instead
    checkArgument(supertype.isAssignableFrom(contextRawType));
    Type genericSupertype = $Gson$Types.getGenericSupertype(context, contextRawType, supertype);
    return $Gson$Types.resolve(context, contextRawType, genericSupertype);
}
