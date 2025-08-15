protected JavaType _narrow(Class<?> subclass)
{
    if (_class == subclass) {
        return this;
    }
    // Should we check that there is a sub-class relationship?
    // 15-Jan-2016, tatu: Almost yes, but there are some complications with
    //    placeholder values (`Void`, `NoClass`), so can not quite do yet.
    // TODO: fix in 2.8
    // Use refine method to handle sub-class refinement properly
    JavaType refined = refine(subclass, _bindings, this._superClass, this._superInterfaces);
    if (refined != null) {
        return refined;
    }
    return new SimpleType(subclass, _bindings, this, _superInterfaces,
            _valueHandler, _typeHandler, _asStatic);
}
