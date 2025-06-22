public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);

    List<String> matchingOpts = new ArrayList<String>();

    // if there is an exact match with a long option, return only that option
    if (hasLongOption(opt))
    {
        matchingOpts.add(opt);
        return matchingOpts;
    }

    // otherwise, add all options that start with opt
    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }

    return matchingOpts;
}
