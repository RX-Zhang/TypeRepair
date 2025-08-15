public BaseSettings withDateFormat(DateFormat df) {
    if (_dateFormat == df) {
        return this;
    }
    TimeZone tz = _timeZone;
    if (df != null) {
        TimeZone dfTz = df.getTimeZone();
        if (dfTz != null) {
            tz = dfTz;
        }
    }
    return new BaseSettings(_classIntrospector, _annotationIntrospector, _visibilityChecker, _propertyNamingStrategy, _typeFactory,
            _typeResolverBuilder, df, _handlerInstantiator, _locale,
            tz, _defaultBase64);
}
