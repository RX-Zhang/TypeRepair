protected JavaType _narrow(Class<?> subclass)
{
    if (_class == subclass) {
        return this;
    }
    // Use the construct method instead of directly calling the constructor
    return SimpleType.construct(subclass);
}
