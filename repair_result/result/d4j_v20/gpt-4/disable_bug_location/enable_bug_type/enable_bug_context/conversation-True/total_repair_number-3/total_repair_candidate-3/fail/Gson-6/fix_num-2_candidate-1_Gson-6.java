static TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson,
    TypeToken<?> fieldType, JsonAdapter annotation) {
  Class<?> value = annotation.value();
  TypeAdapter<?> typeAdapter;
  if (value == null) {
    throw new IllegalArgumentException("@JsonAdapter value cannot be null.");
  }
  if (TypeAdapter.class.isAssignableFrom(value)) {
    Class<TypeAdapter<?>> typeAdapterClass = (Class<TypeAdapter<?>>) value;
    typeAdapter = constructorConstructor.get(TypeToken.get(typeAdapterClass)).construct();
    if (typeAdapter == null) {
      throw new IllegalStateException("Failed to create TypeAdapter instance.");
    }
  } else if (TypeAdapterFactory.class.isAssignableFrom(value)) {
    Class<TypeAdapterFactory> typeAdapterFactory = (Class<TypeAdapterFactory>) value;
    TypeAdapterFactory factory = constructorConstructor.get(TypeToken.get(typeAdapterFactory)).construct();
    if (factory == null) {
      throw new IllegalStateException("Failed to create TypeAdapterFactory instance.");
    }
    typeAdapter = factory.create(gson, fieldType);
    if (typeAdapter == null) {
      throw new IllegalStateException("TypeAdapterFactory returned null TypeAdapter.");
    }
  } else {
    throw new IllegalArgumentException(
        "@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
  }
  typeAdapter = typeAdapter.nullSafe();
  return typeAdapter;
}
