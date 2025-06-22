public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // for a perfect match return the single option only
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.equals(opt)) // Check for exact match
        {
            matchingOpts.clear(); // Clear previous matches
            matchingOpts.add(longOpt);
            return matchingOpts; // Return immediately on exact match
        }
        else if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
