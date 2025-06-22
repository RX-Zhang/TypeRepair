public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);

    List<String> matchingOpts = new ArrayList<String>();

    // Check for perfect match first
    if (hasLongOption(opt))
    {
        matchingOpts.add(opt);
        return matchingOpts;
    }

    // No perfect match found, check for prefix matches
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }

    return matchingOpts;
}
