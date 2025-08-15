public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (clazz == null)
    {
        throw new ParseException("Class parameter cannot be null");
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
        // Ensure returned type matches requested Number subclass if possible
        if (clazz.isInstance(number))
        {
            return (T) number;
        }
        else
        {
            throw new ParseException("Cannot convert to requested number type: " + clazz.getName());
        }
    }
    else if (clazz.equals(Date.class))
    {
        return (T) createDate(str);
    }
    else if (clazz.equals(Class.class))
    {
        return (T) createClass(str);
    }
    else if (clazz.equals(File.class))
    {
        return (T) createFile(str);
    }
    else if (clazz.equals(FileInputStream.class))
    {
        return (T) openFile(str);
    }
    else if (clazz.equals(File[].class))
    {
        return (T) createFiles(str);
    }
    else if (clazz.equals(URL.class))
    {
        return (T) createURL(str);
    }
    else
    {
        throw new ParseException("Unsupported class type: " + clazz.getName());
    }
}
