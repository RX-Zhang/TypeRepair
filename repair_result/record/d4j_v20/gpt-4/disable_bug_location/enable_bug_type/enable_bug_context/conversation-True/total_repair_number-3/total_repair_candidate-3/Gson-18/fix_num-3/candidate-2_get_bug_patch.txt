static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
  checkArgument(supertype.isAssignableFrom(contextRawType));
  Type supertypeType = $Gson$Types.getGenericSupertype(context, contextRawType, supertype);
  Type resolvedType = resolve(context, contextRawType, supertypeType);
  return canonicalize(resolvedType);
}
