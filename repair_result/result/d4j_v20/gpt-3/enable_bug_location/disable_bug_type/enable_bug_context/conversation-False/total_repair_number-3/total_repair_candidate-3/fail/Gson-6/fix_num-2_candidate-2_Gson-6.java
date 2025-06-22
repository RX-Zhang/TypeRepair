static TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson,
    TypeToken<?> fieldType, JsonAdapter annotation) {
  if (annotation == null) {
    throw new IllegalArgumentException("JsonAdapter annotation cannot be null.");
  }

  Class<?> value = annotation.value();
  TypeAdapter<?> typeAdapter;
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

  if (typeAdapter == null) {
    throw new NullPointerException("TypeAdapter cannot be null.");
  }

  typeAdapter = typeAdapter.nullSafe();
  return typeAdapter;
}
