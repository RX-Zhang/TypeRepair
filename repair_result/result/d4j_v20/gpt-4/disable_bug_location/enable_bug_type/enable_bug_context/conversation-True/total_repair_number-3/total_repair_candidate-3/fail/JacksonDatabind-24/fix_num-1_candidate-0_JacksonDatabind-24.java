public BaseSettings withDateFormat(DateFormat df) {
    if (_dateFormat == df) {
        return this;
    }
    // retain the existing timezone if the new DateFormat's timezone is null
    TimeZone tz = (df == null || df.getTimeZone() == null) ? _timeZone : df.getTimeZone();
    return new BaseSettings(_classIntrospector, _annotationIntrospector, _visibilityChecker, _propertyNamingStrategy, _typeFactory,
            _typeResolverBuilder, df, _handlerInstantiator, _locale,
            tz, _defaultBase64);
}
