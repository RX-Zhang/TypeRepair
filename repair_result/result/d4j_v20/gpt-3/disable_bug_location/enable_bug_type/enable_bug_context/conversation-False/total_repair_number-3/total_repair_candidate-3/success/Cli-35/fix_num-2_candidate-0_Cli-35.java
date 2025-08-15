public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // first check for perfect match
    if (longOpts.containsKey(opt)) {
        matchingOpts.add(opt);
        return matchingOpts;
    }

    // otherwise add all options starting with opt
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
