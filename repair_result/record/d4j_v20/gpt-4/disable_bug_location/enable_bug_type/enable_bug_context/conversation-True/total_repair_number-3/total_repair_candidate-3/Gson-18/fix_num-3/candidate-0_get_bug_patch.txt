static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
  checkArgument(supertype.isAssignableFrom(contextRawType));
  Type supertypeType = $Gson$Types.getGenericSupertype(context, contextRawType, supertype);
  Class<?> rawSupertype = $Gson$Types.getRawType(supertypeType);
  if (rawSupertype == supertype) {
    return supertypeType;
  }
  return $Gson$Types.resolve(context, contextRawType, supertypeType);
}
