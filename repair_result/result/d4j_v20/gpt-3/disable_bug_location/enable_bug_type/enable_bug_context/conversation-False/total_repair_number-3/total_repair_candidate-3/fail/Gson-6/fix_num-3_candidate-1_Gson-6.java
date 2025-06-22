static TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson,
    TypeToken<?> fieldType, JsonAdapter annotation) {
  Class<?> value = annotation.value();
  TypeAdapter<?> typeAdapter;
  if (TypeAdapter.class.isAssignableFrom(value)) {
    Class<TypeAdapter<?>> typeAdapterClass = (Class<TypeAdapter<?>>) value;
    try {
      typeAdapter = constructorConstructor.get(TypeToken.get(typeAdapterClass)).construct();
    } catch (Exception e) {
      throw new RuntimeException("Failed to instantiate TypeAdapter: " + typeAdapterClass, e);
    }
  } else if (TypeAdapterFactory.class.isAssignableFrom(value)) {
    Class<TypeAdapterFactory> typeAdapterFactory = (Class<TypeAdapterFactory>) value;
    try {
      typeAdapter = constructorConstructor.get(TypeToken.get(typeAdapterFactory))
          .construct()
          .create(gson, fieldType);
    } catch (Exception e) {
      throw new RuntimeException("Failed to instantiate TypeAdapterFactory: " + typeAdapterFactory, e);
    }
  } else {
    throw new IllegalArgumentException(
        "@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
  }
  if (typeAdapter == null) {
    throw new NullPointerException("TypeAdapter or TypeAdapterFactory created a null TypeAdapter.");
  }
  typeAdapter = typeAdapter.nullSafe();
  return typeAdapter;
}
