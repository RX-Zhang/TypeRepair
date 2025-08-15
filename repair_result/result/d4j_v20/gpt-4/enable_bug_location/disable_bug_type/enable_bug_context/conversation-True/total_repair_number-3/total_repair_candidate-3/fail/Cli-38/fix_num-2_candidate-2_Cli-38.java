private boolean isShortOption(String token) throws ParseException
{
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // Check if it's a single short option with an argument
    if (token.length() == 2)
    {
        return options.hasShortOption(token.substring(1));
    }
    else
    {
        // It may be concatenated short options
        // but first check if the whole opt exists (like -t1)
        String optCandidate = token.substring(1);
        if (options.hasShortOption(optCandidate))
        {
            // handle as one short option with argument or value
            return true;
        }
        else
        {
            // otherwise, handle it as concatenated short options
            handleConcatenatedOptions(token);
            return false; // already handled concatenated options
        }
    }
}
