public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (clazz == null)
    {
        throw new ParseException("Class parameter cannot be null");
    }

    if (PatternOptionBuilder.STRING_VALUE == clazz)
    {
        return (T) str;
    }
    else if (PatternOptionBuilder.OBJECT_VALUE == clazz)
    {
        if (str == null || str.trim().isEmpty()) {
            throw new ParseException("Input string for Object creation cannot be null or empty");
        }
        return (T) createObject(str.trim());
    }
    else if (PatternOptionBuilder.NUMBER_VALUE == clazz)
    {
        if (str == null || str.trim().isEmpty()) {
            throw new ParseException("Input string for Number creation cannot be null or empty");
        }
        return (T) createNumber(str.trim());
    }
    else if (PatternOptionBuilder.DATE_VALUE == clazz)
    {
        if (str == null || str.trim().isEmpty()) {
            throw new ParseException("Input string for Date creation cannot be null or empty");
        }
        return (T) createDate(str.trim());
    }
    else if (PatternOptionBuilder.CLASS_VALUE == clazz)
    {
        if (str == null || str.trim().isEmpty()) {
            throw new ParseException("Input string for Class creation cannot be null or empty");
        }
        return (T) createClass(str.trim());
    }
    else if (PatternOptionBuilder.FILE_VALUE == clazz)
    {
        if (str == null || str.trim().isEmpty()) {
            throw new ParseException("Input string for File creation cannot be null or empty");
        }
        return (T) createFile(str.trim());
    }
    else if (PatternOptionBuilder.EXISTING_FILE_VALUE == clazz)
    {
        if (str == null || str.trim().isEmpty()) {
            throw new ParseException("Input string for opening file cannot be null or empty");
        }
        return (T) openFile(str.trim());
    }
    else if (PatternOptionBuilder.FILES_VALUE == clazz)
    {
        if (str == null || str.trim().isEmpty()) {
            throw new ParseException("Input string for Files creation cannot be null or empty");
        }
        return (T) createFiles(str.trim());
    }
    else if (PatternOptionBuilder.URL_VALUE == clazz)
    {
        if (str == null || str.trim().isEmpty()) {
            throw new ParseException("Input string for URL creation cannot be null or empty");
        }
        return (T) createURL(str.trim());
    }
    else
    {
        throw new ParseException("Unsupported class: " + clazz);
    }
}
