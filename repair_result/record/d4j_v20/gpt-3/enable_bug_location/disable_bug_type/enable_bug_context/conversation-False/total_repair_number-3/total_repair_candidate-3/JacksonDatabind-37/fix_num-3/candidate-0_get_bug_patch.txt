protected JavaType _narrow(Class<?> subclass)
{
    if (_class == subclass) {
        return this;
    }
    // 15-Jan-2016, tatu: Almost yes, but there are some complications with
    //    placeholder values, so no.
    // Commented out check left as is:
    /*
    if (!_class.isAssignableFrom(subclass)) {
        throw new IllegalArgumentException("Class "+subclass.getName()+" not sub-type of "
                +_class.getName());
    }
    */
    // 15-Jan-2015, tatu: Not correct; should really re-resolve...
    return new SimpleType(subclass, _bindings, _superClass, _superInterfaces,
            _valueHandler, _typeHandler, _asStatic);
}
