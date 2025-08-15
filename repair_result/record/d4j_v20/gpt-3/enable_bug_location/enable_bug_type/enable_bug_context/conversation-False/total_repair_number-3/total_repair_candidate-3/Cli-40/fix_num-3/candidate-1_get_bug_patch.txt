public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (clazz == String.class)
    {
        return clazz.cast(str);
    }
    else if (clazz == Object.class)
    {
        return clazz.cast(createObject(str));
    }
    else if (Number.class.isAssignableFrom(clazz))
    {
        return clazz.cast(createNumber(str));
    }
    else if (Date.class.isAssignableFrom(clazz))
    {
        return clazz.cast(createDate(str));
    }
    else if (Class.class.isAssignableFrom(clazz))
    {
        return clazz.cast(createClass(str));
    }
    else if (File.class.isAssignableFrom(clazz))
    {
        return clazz.cast(createFile(str));
    }
    else if (FileInputStream.class.isAssignableFrom(clazz))
    {
        return clazz.cast(openFile(str));
    }
    else if (File[].class.isAssignableFrom(clazz))
    {
        return clazz.cast(createFiles(str));
    }
    else if (URL.class.isAssignableFrom(clazz))
    {
        return clazz.cast(createURL(str));
    }
    else
    {
        throw new ParseException("Unsupported class type: " + clazz.getName());
    }
}
