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

        // Fix: attempt to resolve using constructType with current context to resolve bound types
        Type[] bounds = type.getBounds();
        if (bounds != null && bounds.length > 0) {
            // Add placeholder before constructing to handle recursive types
            context._addPlaceholder(name);
            return _constructType(bounds[0], context);
        }
    }

    // If no context and no bounds, fallback to unknown type
    return _unknownType();
}
