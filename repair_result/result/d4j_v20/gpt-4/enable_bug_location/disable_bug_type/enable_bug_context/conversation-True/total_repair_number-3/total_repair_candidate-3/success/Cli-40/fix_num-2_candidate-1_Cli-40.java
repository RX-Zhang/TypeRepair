public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (PatternOptionBuilder.STRING_VALUE.equals(clazz))
    {
        return (T) str;
    }
    else if (PatternOptionBuilder.OBJECT_VALUE.equals(clazz))
    {
        return (T) createObject(str);
    }
    else if (PatternOptionBuilder.NUMBER_VALUE.equals(clazz))
    {
        return (T) createNumber(str);
    }
    else if (PatternOptionBuilder.DATE_VALUE.equals(clazz))
    {
        return (T) createDate(str);
    }
    else if (PatternOptionBuilder.CLASS_VALUE.equals(clazz))
    {
        return (T) createClass(str);
    }
    else if (PatternOptionBuilder.FILE_VALUE.equals(clazz))
    {
        return (T) createFile(str);
    }
    else if (PatternOptionBuilder.EXISTING_FILE_VALUE.equals(clazz))
    {
        return (T) openFile(str);
    }
    else if (PatternOptionBuilder.FILES_VALUE.equals(clazz))
    {
        return (T) createFiles(str);
    }
    else if (PatternOptionBuilder.URL_VALUE.equals(clazz))
    {
        return (T) createURL(str);
    }
    else
    {
        throw new ParseException("Cannot convert string to " + clazz.getName());
    }
}
