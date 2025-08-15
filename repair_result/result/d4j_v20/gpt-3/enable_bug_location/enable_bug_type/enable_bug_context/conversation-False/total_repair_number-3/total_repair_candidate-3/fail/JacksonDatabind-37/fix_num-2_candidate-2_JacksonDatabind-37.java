protected JavaType _narrow(Class<?> subclass)
{
    if (_class == subclass) {
        return this;
    }
    // Check that there is a sub-class relationship
    if (!_class.isAssignableFrom(subclass)) {
        throw new IllegalArgumentException("Class " + subclass.getName() + " is not a sub-type of " + _class.getName());
    }
    // Correctly resolve the parameters for the new SimpleType
    return new SimpleType(subclass, _bindings, _superClass, _superInterfaces,
            _valueHandler, _typeHandler, _asStatic);
}
