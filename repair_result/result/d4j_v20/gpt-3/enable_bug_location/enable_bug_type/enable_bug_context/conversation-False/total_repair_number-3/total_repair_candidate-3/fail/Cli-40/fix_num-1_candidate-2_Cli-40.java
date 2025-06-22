public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (clazz == String.class)
    {
        return (T) str;
    }
    else if (clazz == Object.class)
    {
        return (T) createObject(str);
    }
    else if (Number.class.isAssignableFrom(clazz))
    {
        return (T) createNumber(str);
    }
    else if (Date.class.isAssignableFrom(clazz))
    {
        return (T) createDate(str);
    }
    else if (Class.class.isAssignableFrom(clazz))
    {
        return (T) createClass(str);
    }
    else if (File.class.isAssignableFrom(clazz))
    {
        return (T) createFile(str);
    }
    else if (File.class.isAssignableFrom(clazz))
    {
        return (T) openFile(str);
    }
    else if (File[].class.isAssignableFrom(clazz))
    {
        return (T) createFiles(str);
    }
    else if (URL.class.isAssignableFrom(clazz))
    {
        return (T) createURL(str);
    }
    else
    {
        throw new ParseException("Unsupported type: " + clazz.getName());
    }
}
