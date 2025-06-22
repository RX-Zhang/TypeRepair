public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (clazz == null) {
        throw new ParseException("Class parameter cannot be null");
    }

    if (clazz == PatternOptionBuilder.STRING_VALUE)
    {
        return (T) str;
    }
    else if (clazz == PatternOptionBuilder.OBJECT_VALUE)
    {
        return (T) createObject(str);
    }
    else if (clazz == PatternOptionBuilder.NUMBER_VALUE)
    {
        return (T) createNumber(str);
    }
    else if (clazz == PatternOptionBuilder.DATE_VALUE)
    {
        return (T) createDate(str);
    }
    else if (clazz == PatternOptionBuilder.CLASS_VALUE)
    {
        return (T) createClass(str);
    }
    else if (clazz == PatternOptionBuilder.FILE_VALUE)
    {
        return (T) createFile(str);
    }
    else if (clazz == PatternOptionBuilder.EXISTING_FILE_VALUE)
    {
        return (T) openFile(str);
    }
    else if (clazz == PatternOptionBuilder.FILES_VALUE)
    {
        return (T) createFiles(str);
    }
    else if (clazz == PatternOptionBuilder.URL_VALUE)
    {
        return (T) createURL(str);
    }
    else
    {
        throw new ParseException("Cannot convert string to given class type: " + clazz.getName());
    }
}
