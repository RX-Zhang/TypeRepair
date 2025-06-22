private boolean _hasCustomHandlers(JavaType t) {
    if (t.isContainerType()) {
        // First: value types may have both value and type handlers
        JavaType ct = t.getContentType();
        if (ct != null) {
            // Also check key type handlers for map-like types
            JavaType kt = t.getKeyType();
            return ((kt != null) && ((kt.getValueHandler() != null) || (kt.getTypeHandler() != null)))
                    || (ct.getValueHandler() != null) 
                    || (ct.getTypeHandler() != null);
        }
    }
    return false;
}
