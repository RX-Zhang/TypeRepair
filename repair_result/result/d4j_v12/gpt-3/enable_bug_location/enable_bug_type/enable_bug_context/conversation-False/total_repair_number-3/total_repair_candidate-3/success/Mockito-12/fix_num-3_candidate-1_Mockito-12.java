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
        // fallback in case actual is not a Class or ParameterizedType
        return Object.class;
    }
    
    return Object.class;
}
