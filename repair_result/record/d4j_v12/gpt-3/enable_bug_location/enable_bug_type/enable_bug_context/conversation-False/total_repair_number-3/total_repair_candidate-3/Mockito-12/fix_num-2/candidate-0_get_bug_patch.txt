public Class getGenericType(Field field) {        
    Type generic = field.getGenericType();
    if (generic instanceof ParameterizedType) {
        Type actual = ((ParameterizedType) generic).getActualTypeArguments()[0];

        // actual can be a Class or another ParameterizedType, handle accordingly
        if (actual instanceof Class) {
            return (Class) actual;
        } else if (actual instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) actual).getRawType();
            if (rawType instanceof Class) {
                return (Class) rawType;
            }
        }
        // could add more handling if needed, else fallback
    }
    
    return Object.class;
}
