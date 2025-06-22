public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (clazz == null) {
        throw new ParseException("Target class must not be null");
    }
    if (clazz.equals(String.class))
    {
        return (T) str;
    }
    else if (clazz.equals(Object.class))
    {
        return (T) createObject(str);
    }
    else if (Number.class.isAssignableFrom(clazz))
    {
        Number number = createNumber(str);
        if (clazz.isInstance(number))
        {
            return (T) number;
        }
        else
        {
            throw new ParseException("Number type mismatch: cannot convert " + str + " to " + clazz.getName());
        }
    }
    else if (clazz.equals(java.util.Date.class))
    {
        return (T) createDate(str);
    }
    else if (Class.class.equals(clazz))
    {
        return (T) createClass(str);
    }
    else if (File.class.equals(clazz))
    {
        return (T) createFile(str);
    }
    else if (java.io.FileInputStream.class.equals(clazz))
    {
        return (T) openFile(str);
    }
    else if (File[].class.equals(clazz))
    {
        return (T) createFiles(str);
    }
    else if (URL.class.equals(clazz))
    {
        return (T) createURL(str);
    }
    else
    {
        throw new ParseException("Unsupported target class: " + clazz.getName());
    }
}
