public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // first check for an exact long option match and return that only
    if (hasLongOption(opt)) {
        matchingOpts.add(opt);
        return matchingOpts;
    }

    // otherwise find all options that start with the provided opt
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
