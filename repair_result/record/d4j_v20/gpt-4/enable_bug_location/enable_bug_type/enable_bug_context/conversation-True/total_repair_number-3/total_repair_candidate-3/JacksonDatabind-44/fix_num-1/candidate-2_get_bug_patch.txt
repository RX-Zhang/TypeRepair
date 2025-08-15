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
    throw new IllegalArgumentException("Class "+subclass.getName()+" not sub-type of "
            +_class.getName());
            */
    JavaType refined = refine(subclass, _bindings, _superClass, _superInterfaces);
    if (refined == null) {
        // no refinement available, so just create a new SimpleType instance
        return new SimpleType(subclass, _bindings, this, _superInterfaces,
                _valueHandler, _typeHandler, _asStatic);
    }
    return refined;
    // Otherwise, stitch together the hierarchy. First, super-class
    // if not found, try a super-interface
    // should not get here but...
}
