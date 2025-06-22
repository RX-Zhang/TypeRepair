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

    // Otherwise, find all options that start with opt
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }

    return matchingOpts;
}
