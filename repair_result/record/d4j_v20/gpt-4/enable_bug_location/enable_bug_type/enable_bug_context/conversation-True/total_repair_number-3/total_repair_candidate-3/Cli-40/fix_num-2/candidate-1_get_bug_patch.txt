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
    else if (clazz == Date.class)
    {
        return (T) createDate(str);
    }
    else if (clazz == Class.class)
    {
        return (T) createClass(str);
    }
    else if (clazz == File.class)
    {
        return (T) createFile(str);
    }
    else if (clazz == FileInputStream.class)
    {
        return (T) openFile(str);
    }
    else if (clazz == File[].class)
    {
        return (T) createFiles(str);
    }
    else if (clazz == URL.class)
    {
        return (T) createURL(str);
    }
    else
    {
        return null;
    }
}
