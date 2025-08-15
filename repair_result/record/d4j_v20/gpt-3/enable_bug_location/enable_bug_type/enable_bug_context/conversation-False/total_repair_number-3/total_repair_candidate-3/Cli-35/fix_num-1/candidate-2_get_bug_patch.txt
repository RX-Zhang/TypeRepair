public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // for a perfect match return the single option only
    // Change the logic to only add a matching option if it matches exactly
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.equals(opt)) // Change startsWith to equals for exact match
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
