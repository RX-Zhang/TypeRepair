protected JavaType _narrow(Class<?> subclass)
{
    if (_class == subclass) {
        return this;
    }
    // Use the construct method to ensure proper SimpleType creation with sanity checks
    return SimpleType.construct(subclass);
}
