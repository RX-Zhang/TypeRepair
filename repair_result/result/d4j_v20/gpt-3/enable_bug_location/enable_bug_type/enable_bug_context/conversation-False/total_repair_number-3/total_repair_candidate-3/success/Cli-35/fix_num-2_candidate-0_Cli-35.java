public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // for a perfect match return the single option only
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.equals(opt)) // Check for exact match first
        {
            matchingOpts.clear(); // Clear any previous matches
            matchingOpts.add(longOpt);
            return matchingOpts; // Return immediately if an exact match is found
        }
        else if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
