public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // for a perfect match return the single option only
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.equals(opt)) // Change to equals for exact match
        {
            matchingOpts.clear(); // Clear previous matches
            matchingOpts.add(longOpt);
            return matchingOpts; // Return immediately for exact match
        }
        else if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
