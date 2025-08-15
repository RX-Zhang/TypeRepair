public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    // if there is an exact match for the long option, return only that
    if (hasLongOption(opt))
    {
        return Collections.singletonList(opt);
    }
    
    List<String> matchingOpts = new ArrayList<String>();

    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
