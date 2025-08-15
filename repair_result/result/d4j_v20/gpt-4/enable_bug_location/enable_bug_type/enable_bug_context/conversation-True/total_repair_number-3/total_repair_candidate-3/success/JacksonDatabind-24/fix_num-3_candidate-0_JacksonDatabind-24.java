public BaseSettings withDateFormat(DateFormat df) {
    if (_dateFormat == df) {
        return this;
    }
    // Do not override the existing _timeZone with df.getTimeZone(); keep the original _timeZone intact
    return new BaseSettings(_classIntrospector, _annotationIntrospector, _visibilityChecker, _propertyNamingStrategy, _typeFactory,
            _typeResolverBuilder, df, _handlerInstantiator, _locale,
            _timeZone, _defaultBase64);
}
