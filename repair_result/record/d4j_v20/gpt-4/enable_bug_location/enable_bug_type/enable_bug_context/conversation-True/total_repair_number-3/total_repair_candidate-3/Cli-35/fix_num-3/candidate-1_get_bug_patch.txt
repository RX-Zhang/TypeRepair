public List<String> getMatchingOptions(String opt)
{
    opt = Util.stripLeadingHyphens(opt);

    List<String> matchingOpts = new ArrayList<String>();

    // if there is an exact match, return only that option
    if (hasLongOption(opt)) {
        matchingOpts.add(opt);
        return matchingOpts;
    }

    for (String longOpt : longOpts.keySet())
    {
        if (longOpt.startsWith(opt))
        {
            matchingOpts.add(longOpt);
        }
    }

    return matchingOpts;
}
