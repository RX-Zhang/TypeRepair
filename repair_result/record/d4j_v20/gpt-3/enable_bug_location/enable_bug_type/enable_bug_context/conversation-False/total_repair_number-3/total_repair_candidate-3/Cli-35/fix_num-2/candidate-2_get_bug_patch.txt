public List<String> getMatchingOptions(String opt)
{
    if (opt == null || opt.isEmpty()) {
        throw new IllegalArgumentException("Option cannot be null or empty");
    }
    
    opt = Util.stripLeadingHyphens(opt);
    
    List<String> matchingOpts = new ArrayList<String>();

    // Loop to find matches; if a single match found, return immediately
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.equals(opt)) {
            matchingOpts.clear(); // clear any previous matches
            matchingOpts.add(longOpt); // return only this match
            return matchingOpts;
        }
        else if (longOpt.startsWith(opt)) {
            matchingOpts.add(longOpt);
        }
    }
    
    return matchingOpts;
}
