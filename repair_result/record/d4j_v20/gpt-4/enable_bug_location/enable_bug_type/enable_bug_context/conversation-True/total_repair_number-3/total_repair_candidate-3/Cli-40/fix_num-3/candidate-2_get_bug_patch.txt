public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (String.class == clazz)
    {
        return (T) str;
    }
    else if (Object.class == clazz)
    {
        return (T) createObject(str);
    }
    else if (Number.class == clazz)
    {
        return (T) createNumber(str);
    }
    else if (Date.class == clazz)
    {
        return (T) createDate(str);
    }
    else if (Class.class == clazz)
    {
        return (T) createClass(str);
    }
    else if (File.class == clazz)
    {
        return (T) createFile(str);
    }
    else if (FileInputStream.class == clazz)
    {
        return (T) openFile(str);
    }
    else if (File[].class == clazz)
    {
        return (T) createFiles(str);
    }
    else if (URL.class == clazz)
    {
        return (T) createURL(str);
    }
    else
    {
        return null;
    }
}
