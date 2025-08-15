public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (clazz == null)
    {
        throw new ParseException("Target class must not be null");
    }

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
        Number number = createNumber(str);
        if (clazz.isInstance(number))
        {
            return (T) number;
        }
        else if (clazz == Integer.class)
        {
            return (T) Integer.valueOf(number.intValue());
        }
        else if (clazz == Long.class)
        {
            return (T) Long.valueOf(number.longValue());
        }
        else if (clazz == Double.class)
        {
            return (T) Double.valueOf(number.doubleValue());
        }
        else if (clazz == Float.class)
        {
            return (T) Float.valueOf(number.floatValue());
        }
        else if (clazz == Short.class)
        {
            return (T) Short.valueOf(number.shortValue());
        }
        else if (clazz == Byte.class)
        {
            return (T) Byte.valueOf(number.byteValue());
        }
        else
        {
            throw new ParseException("Unsupported Number subclass: " + clazz.getName());
        }
    }
    else if (clazz == java.util.Date.class)
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
        throw new ParseException("Unsupported target class: " + clazz.getName());
    }
}
