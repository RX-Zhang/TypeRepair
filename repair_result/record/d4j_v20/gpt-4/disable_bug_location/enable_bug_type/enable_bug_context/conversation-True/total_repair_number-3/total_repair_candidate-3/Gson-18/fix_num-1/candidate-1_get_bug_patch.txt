static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
    checkArgument(supertype.isAssignableFrom(contextRawType));
    Type supertypeType = $Gson$Types.getGenericSupertype(context, contextRawType, supertype);
    Class<?> rawType = $Gson$Types.getRawType(supertypeType);
    return resolve(context, contextRawType, supertypeType);
}
