public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // first check if there is an exact match, if so, return only that
    if (longOpts.containsKey(opt)) {
        matchingOpts.add(opt);
        return matchingOpts;
    }

    // else return all options that start with opt
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
