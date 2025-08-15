static TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson,
    TypeToken<?> fieldType, JsonAdapter annotation) {
  Class<?> value = annotation.value();
  TypeAdapter<?> typeAdapter;
  
  // Validate the annotation value type
  if (value == null) {
    throw new IllegalArgumentException("Annotation value cannot be null.");
  }

  if (TypeAdapter.class.isAssignableFrom(value)) {
    Class<TypeAdapter<?>> typeAdapterClass = (Class<TypeAdapter<?>>) value;
    typeAdapter = constructorConstructor.get(TypeToken.get(typeAdapterClass)).construct();
  } else if (TypeAdapterFactory.class.isAssignableFrom(value)) {
    Class<TypeAdapterFactory> typeAdapterFactory = (Class<TypeAdapterFactory>) value;
    typeAdapter = constructorConstructor.get(TypeToken.get(typeAdapterFactory))
        .construct()
        .create(gson, fieldType);
  } else {
    throw new IllegalArgumentException(
        "@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
  }
  
  // Ensure typeAdapter is not null before calling nullSafe()
  if (typeAdapter == null) {
    throw new IllegalStateException("TypeAdapter cannot be null.");
  }
  
  typeAdapter = typeAdapter.nullSafe();
  return typeAdapter;
}
