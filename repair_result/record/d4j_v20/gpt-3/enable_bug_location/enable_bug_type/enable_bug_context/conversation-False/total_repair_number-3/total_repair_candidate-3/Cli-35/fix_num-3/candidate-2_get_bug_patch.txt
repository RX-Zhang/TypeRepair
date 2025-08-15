public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // for a perfect match return the single option only
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.equals(opt)) // Changed from startsWith to equals for exact match
        {
            matchingOpts.add(longOpt);
            return matchingOpts; // Return immediately for a perfect match
        }
    }

    // If no exact match was found, gather all startsWith matches
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
