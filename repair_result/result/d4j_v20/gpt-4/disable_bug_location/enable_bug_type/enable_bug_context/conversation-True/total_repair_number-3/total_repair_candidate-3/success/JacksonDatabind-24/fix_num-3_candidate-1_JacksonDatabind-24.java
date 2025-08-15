public BaseSettings withDateFormat(DateFormat df) {
    if (_dateFormat == df) {
        return this;
    }
    // keep the existing time zone instead of changing based on the DateFormat
    return new BaseSettings(_classIntrospector, _annotationIntrospector, _visibilityChecker, _propertyNamingStrategy, _typeFactory,
            _typeResolverBuilder, df, _handlerInstantiator, _locale,
            _timeZone, _defaultBase64);
}
