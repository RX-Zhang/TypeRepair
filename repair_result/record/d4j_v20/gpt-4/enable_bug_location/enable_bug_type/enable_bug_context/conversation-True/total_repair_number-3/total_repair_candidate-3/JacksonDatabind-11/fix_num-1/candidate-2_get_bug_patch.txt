protected JavaType _fromVariable(TypeVariable<?> type, TypeBindings context)
{
    final String name = type.getName();
    // 19-Mar-2015: Without context, all we can check are bounds.
    if (context == null) {
        // And to prevent infinite loops, now need this:
        return _unknownType();
    } else {
        // Ok: here's where context might come in handy!
        /* 19-Mar-2015, tatu: As per [databind#609], may need to allow
         *   unresolved type variables to handle some cases where bounds
         *   are enough. Let's hope it does not hide real fail cases.
         */
        JavaType actualType = context.findType(name);
        if (actualType != null) {
            return actualType;
        }
        // Use constructType to resolve bounds when actualType not found in context
        // This is the fix: try to resolve bounds if variable is not found.
        Type[] bounds = type.getBounds();
        if (bounds.length > 0) {
            return _constructType(bounds[0], context);
        }
    }

    /* 29-Jan-2010, tatu: One more problem are recursive types
     *   (T extends Comparable<T>). Need to add "placeholder"
     *   for resolution to catch those.
     */
    context._addPlaceholder(name);
    return _unknownType();
}
