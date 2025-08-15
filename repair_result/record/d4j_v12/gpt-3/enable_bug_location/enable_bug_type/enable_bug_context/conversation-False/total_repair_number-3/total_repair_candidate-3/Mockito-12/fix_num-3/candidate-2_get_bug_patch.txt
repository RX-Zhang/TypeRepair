public Class getGenericType(Field field) {        
    Type generic = field.getGenericType();
    if (generic != null && generic instanceof ParameterizedType) {
        Type actual = ((ParameterizedType) generic).getActualTypeArguments()[0];
        if (actual instanceof Class) {
            return (Class) actual;
        } else if (actual instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) actual).getRawType();
            if (rawType instanceof Class) {
                return (Class) rawType;
            }
        }
        // fallback if the type is not a class or parameterized type
        return Object.class;
    }
    
    return Object.class;
}
