public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (PatternOptionBuilder.STRING_VALUE == clazz)
    {
        return clazz.cast(str);
    }
    else if (PatternOptionBuilder.OBJECT_VALUE == clazz)
    {
        return clazz.cast(createObject(str));
    }
    else if (PatternOptionBuilder.NUMBER_VALUE == clazz)
    {
        return clazz.cast(createNumber(str));
    }
    else if (PatternOptionBuilder.DATE_VALUE == clazz)
    {
        return clazz.cast(createDate(str));
    }
    else if (PatternOptionBuilder.CLASS_VALUE == clazz)
    {
        return clazz.cast(createClass(str));
    }
    else if (PatternOptionBuilder.FILE_VALUE == clazz)
    {
        return clazz.cast(createFile(str));
    }
    else if (PatternOptionBuilder.EXISTING_FILE_VALUE == clazz)
    {
        return clazz.cast(openFile(str));
    }
    else if (PatternOptionBuilder.FILES_VALUE == clazz)
    {
        return clazz.cast(createFiles(str));
    }
    else if (PatternOptionBuilder.URL_VALUE == clazz)
    {
        return clazz.cast(createURL(str));
    }
    else
    {
        return null;
    }
}
