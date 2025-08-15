public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    // If an exact match exists, return only that option
    if (longOpts.containsKey(opt)) {
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
