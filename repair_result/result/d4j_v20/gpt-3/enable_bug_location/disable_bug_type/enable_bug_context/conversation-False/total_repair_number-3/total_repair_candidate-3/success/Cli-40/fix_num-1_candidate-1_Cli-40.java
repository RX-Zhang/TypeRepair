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
        // If the target type is an Enum, attempt to convert the string to the enum constant
        if (clazz.isEnum()) {
            try {
                return (T) Enum.valueOf((Class<Enum>) clazz.asSubclass(Enum.class), str);
            } catch (IllegalArgumentException e) {
                throw new ParseException("No enum constant " + clazz.getCanonicalName() + "." + str);
            }
        }
        
        // Attempt to handle common types like Integer, Long, Double, Boolean, etc.
        if (clazz == Integer.class) {
            try {
                return (T) Integer.valueOf(str);
            } catch (NumberFormatException e) {
                throw new ParseException("Unable to parse Integer: " + str);
            }
        } else if (clazz == Long.class) {
            try {
                return (T) Long.valueOf(str);
            } catch (NumberFormatException e) {
                throw new ParseException("Unable to parse Long: " + str);
            }
        } else if (clazz == Double.class) {
            try {
                return (T) Double.valueOf(str);
            } catch (NumberFormatException e) {
                throw new ParseException("Unable to parse Double: " + str);
            }
        } else if (clazz == Float.class) {
            try {
                return (T) Float.valueOf(str);
            } catch (NumberFormatException e) {
                throw new ParseException("Unable to parse Float: " + str);
            }
        } else if (clazz == Boolean.class) {
            // Accept "true" or "false" ignoring case
            if ("true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str)) {
                return (T) Boolean.valueOf(str);
            } else {
                throw new ParseException("Unable to parse Boolean: " + str);
            }
        }
        
        throw new ParseException("Unsupported type: " + clazz.getName());
    }
}
