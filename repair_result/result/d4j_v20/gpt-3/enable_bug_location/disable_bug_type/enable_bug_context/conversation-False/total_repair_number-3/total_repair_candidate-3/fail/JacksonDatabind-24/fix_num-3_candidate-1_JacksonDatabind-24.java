public BaseSettings withDateFormat(DateFormat df) {
    if (_dateFormat != null && _dateFormat.equals(df)) {
        return this;
    }
    TimeZone tz = (df == null) ? _timeZone : df.getTimeZone();
    return new BaseSettings(_classIntrospector, _annotationIntrospector, _visibilityChecker, _propertyNamingStrategy, _typeFactory,
            _typeResolverBuilder, df, _handlerInstantiator, _locale,
            tz, _defaultBase64);
}
