public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // if there is an exact match, return it as the only matching option
    if (longOpts.containsKey(opt)) {
        matchingOpts.add(opt);
        return matchingOpts;
    }

    // otherwise, add all options that start with the given prefix
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
