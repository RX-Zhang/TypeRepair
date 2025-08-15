public boolean isCachable() {
    /* As per [databind#735], existence of value deserializer,
     * type deserializer, or ignorable properties (only passed
     * if annotated to use non-standard one) should also prevent caching.
     */
    return (_valueTypeDeserializer == null)
            && (_valueDeserializer == null)
            && (_ignorableProperties == null);
}
