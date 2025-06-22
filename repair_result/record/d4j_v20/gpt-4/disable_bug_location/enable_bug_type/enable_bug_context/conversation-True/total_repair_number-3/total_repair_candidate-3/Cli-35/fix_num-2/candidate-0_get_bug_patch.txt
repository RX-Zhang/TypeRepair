public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // Return perfect match only if it exists
    if (hasLongOption(opt))
    {
        matchingOpts.add(opt);
        return matchingOpts;
    }

    // Otherwise add all options that start with the input
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
