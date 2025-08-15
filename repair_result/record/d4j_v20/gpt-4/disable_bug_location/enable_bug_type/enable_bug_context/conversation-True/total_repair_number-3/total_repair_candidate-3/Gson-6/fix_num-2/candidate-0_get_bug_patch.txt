static TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson,
    TypeToken<?> fieldType, JsonAdapter annotation) {
  Class<?> value = annotation.value();
  TypeAdapter<?> typeAdapter;
  if (TypeAdapter.class.isAssignableFrom(value)) {
    Class<TypeAdapter<?>> typeAdapterClass = (Class<TypeAdapter<?>>) value;
    typeAdapter = constructorConstructor.get(TypeToken.get(typeAdapterClass)).construct();
  } else if (TypeAdapterFactory.class.isAssignableFrom(value)) {
    Class<TypeAdapterFactory> typeAdapterFactory = (Class<TypeAdapterFactory>) value;
    TypeAdapterFactory factory = constructorConstructor.get(TypeToken.get(typeAdapterFactory)).construct();
    if (factory == null) {
      throw new IllegalStateException("TypeAdapterFactory constructor returned null instance.");
    }
    typeAdapter = factory.create(gson, fieldType);
  } else {
    throw new IllegalArgumentException(
        "@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
  }
  if (typeAdapter == null) {
    throw new IllegalStateException("TypeAdapter returned by TypeAdapterFactory was null.");
  }
  typeAdapter = typeAdapter.nullSafe();
  return typeAdapter;
}
