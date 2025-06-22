protected JavaType _narrow(Class<?> subclass)
{
    if (_class == subclass) {
        return this;
    }
    if (!_class.isAssignableFrom(subclass)) {
        throw new IllegalArgumentException("Class "+subclass.getName()+" not sub-type of "
                +_class.getName());
    }
    JavaType superClass = _superClass;
    JavaType[] superInts = _superInterfaces;
    TypeBindings bindings = _bindings;

    JavaType refined = refine(subclass, bindings, superClass, superInts);
    if (refined != null) {
        return refined;
    }
    return new SimpleType(subclass, bindings, this, superInts,
            _valueHandler, _typeHandler, _asStatic);
}
