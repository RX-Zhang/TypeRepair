public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // Check if there is an exact match first
    if (hasLongOption(opt)) {
        matchingOpts.add(opt);
        return matchingOpts;
    }

    // Otherwise add all long options that start with opt
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
