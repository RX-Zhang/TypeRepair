static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
    // wildcards are useless for resolving supertypes. As the upper bound has the same raw type, use it instead
    checkArgument(supertype.isAssignableFrom(getRawType(context))); // Convert context to raw type for comparison
    return resolve(context, contextRawType,
        $Gson$Types.getGenericSupertype(context, contextRawType, supertype));
}
