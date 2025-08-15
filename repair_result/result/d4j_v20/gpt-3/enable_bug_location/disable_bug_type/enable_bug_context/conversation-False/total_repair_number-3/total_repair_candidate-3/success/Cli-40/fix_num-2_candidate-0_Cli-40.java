public static <T> T createValue(final String str, final Class<T> clazz) throws ParseException
{
    if (PatternOptionBuilder.STRING_VALUE == clazz)
    {
        return (T) str;
    }
    else if (PatternOptionBuilder.OBJECT_VALUE == clazz)
    {
        return (T) createObject(str);
    }
    else if (PatternOptionBuilder.NUMBER_VALUE == clazz)
    {
        return (T) createNumber(str);
    }
    else if (PatternOptionBuilder.DATE_VALUE == clazz)
    {
        return (T) createDate(str);
    }
    else if (PatternOptionBuilder.CLASS_VALUE == clazz)
    {
        return (T) createClass(str);
    }
    else if (PatternOptionBuilder.FILE_VALUE == clazz)
    {
        return (T) createFile(str);
    }
    else if (PatternOptionBuilder.EXISTING_FILE_VALUE == clazz)
    {
        return (T) openFile(str);
    }
    else if (PatternOptionBuilder.FILES_VALUE == clazz)
    {
        return (T) createFiles(str);
    }
    else if (PatternOptionBuilder.URL_VALUE == clazz)
    {
        return (T) createURL(str);
    }
    else
    {
        // Attempt to convert string to the requested type if possible
        if (clazz == Integer.class)
        {
            try
            {
                return clazz.cast(Integer.valueOf(str));
            }
            catch (final NumberFormatException e)
            {
                throw new ParseException("Unable to parse integer: " + str);
            }
        }
        else if (clazz == Long.class)
        {
            try
            {
                return clazz.cast(Long.valueOf(str));
            }
            catch (final NumberFormatException e)
            {
                throw new ParseException("Unable to parse long: " + str);
            }
        }
        else if (clazz == Double.class)
        {
            try
            {
                return clazz.cast(Double.valueOf(str));
            }
            catch (final NumberFormatException e)
            {
                throw new ParseException("Unable to parse double: " + str);
            }
        }
        else if (clazz == Boolean.class)
        {
            return clazz.cast(Boolean.valueOf(str));
        }
        else
        {
            throw new ParseException("Unable to create an instance of the type: " + clazz.getName());
        }
    }
}
