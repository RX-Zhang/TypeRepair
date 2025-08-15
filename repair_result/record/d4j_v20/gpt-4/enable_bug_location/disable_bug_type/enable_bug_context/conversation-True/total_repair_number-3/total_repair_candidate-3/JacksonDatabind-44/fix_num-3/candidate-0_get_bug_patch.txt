protected JavaType _narrow(Class<?> subclass)
{
    if (_class == subclass) {
        return this;
    }
    // Check that the subclass is actually a sub-class of _class
    if (!_class.isAssignableFrom(subclass)) {
        throw new IllegalArgumentException("Class "+subclass.getName()+" not sub-type of "
                +_class.getName());
    }
    // Use refine to stitch together the hierarchy properly
    JavaType superClassType = _superClass;
    JavaType[] superInterfaces = _superInterfaces;
    return refine(subclass, _bindings, superClassType, superInterfaces);
}
