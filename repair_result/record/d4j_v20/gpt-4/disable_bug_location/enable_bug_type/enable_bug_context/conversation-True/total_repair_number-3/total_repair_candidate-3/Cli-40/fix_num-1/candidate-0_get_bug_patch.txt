public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (clazz == null)
    {
        throw new ParseException("Class type cannot be null");
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
        // createNumber returns Number, so we need to cast carefully
        Number number = createNumber(str);
        if (clazz.isInstance(number))
        {
            return (T) number;
        }
        else
        {
            throw new ParseException("Cannot convert number to class: " + clazz.getName());
        }
    }
    else if (clazz.equals(java.util.Date.class))
    {
        return (T) createDate(str);
    }
    else if (clazz.equals(Class.class))
    {
        return (T) createClass(str);
    }
    else if (clazz.equals(java.io.File.class))
    {
        return (T) createFile(str);
    }
    else if (clazz.equals(java.io.FileInputStream.class))
    {
        return (T) openFile(str);
    }
    else if (clazz.equals(java.io.File[].class))
    {
        return (T) createFiles(str);
    }
    else if (clazz.equals(java.net.URL.class))
    {
        return (T) createURL(str);
    }
    else
    {
        throw new ParseException("Unsupported class type: " + clazz.getName());
    }
}
