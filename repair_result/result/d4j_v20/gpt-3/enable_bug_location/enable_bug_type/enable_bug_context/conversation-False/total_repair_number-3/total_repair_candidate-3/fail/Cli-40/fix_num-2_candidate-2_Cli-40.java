public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (clazz == String.class) // Fixed type check
    {
        return (T) str;
    }
    else if (clazz == Object.class) // Fixed type check
    {
        return (T) createObject(str);
    }
    else if (Number.class.isAssignableFrom(clazz)) // Fixed type check for Number
    {
        return (T) createNumber(str);
    }
    else if (Date.class.isAssignableFrom(clazz)) // Fixed type check for Date
    {
        return (T) createDate(str);
    }
    else if (Class.class.isAssignableFrom(clazz)) // Fixed type check for Class
    {
        return (T) createClass(str);
    }
    else if (File.class.isAssignableFrom(clazz)) // Fixed type check for File
    {
        return (T) createFile(str);
    }
    else if (File.class.isAssignableFrom(clazz)) // Fixed type check for existing File
    {
        return (T) openFile(str);
    }
    else if (File[].class.isAssignableFrom(clazz)) // Fixed type check for File[]
    {
        return (T) createFiles(str);
    }
    else if (URL.class.isAssignableFrom(clazz)) // Fixed type check for URL
    {
        return (T) createURL(str);
    }
    else
    {
        throw new ParseException("Unsupported type: " + clazz.getName());
    }
}
