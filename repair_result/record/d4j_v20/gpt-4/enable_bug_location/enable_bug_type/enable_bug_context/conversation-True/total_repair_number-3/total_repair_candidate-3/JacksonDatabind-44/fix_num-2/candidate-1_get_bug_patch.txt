protected JavaType _narrow(Class<?> subclass)
{
    if (_class == subclass) {
        return this;
    }
    // Should we check that there is a sub-class relationship?
    // 15-Jan-2016, tatu: Almost yes, but there are some complications with
    //    placeholder values (`Void`, `NoClass`), so can not quite do yet.
    // TODO: fix in 2.8
    /*
    if (!_class.isAssignableFrom(subclass)) {
        throw new IllegalArgumentException("Class "+subclass.getName()+" not sub-type of "
            +_class.getName());
    }
    */
    // Use refine to produce a narrowed type, if possible
    JavaType refined = refine(subclass, _bindings, _superClass, _superInterfaces);
    if (refined != null) {
        return refined;
    }
    // Otherwise, stitch together the hierarchy by creating a new SimpleType
    return new SimpleType(subclass, _bindings, this, _superInterfaces,
            _valueHandler, _typeHandler, _asStatic);
}
