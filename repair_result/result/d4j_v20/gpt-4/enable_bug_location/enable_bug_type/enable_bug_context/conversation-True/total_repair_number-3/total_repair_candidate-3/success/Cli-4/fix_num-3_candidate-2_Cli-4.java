private void checkRequiredOptions()
    throws MissingOptionException
{
    // if there are required options that have not been
    // processed
    if (requiredOptions.size() > 0)
    {
        Iterator iter = requiredOptions.iterator();
        StringBuffer buff = new StringBuffer();

        // if there are multiple missing options, prepend proper message
        if (requiredOptions.size() > 1) {
            buff.append("Missing required options: ");
        } else {
            buff.append("Missing required option: ");
        }

        // loop through the required options
        while (iter.hasNext())
        {
            buff.append(iter.next());
        }

        throw new MissingOptionException(buff.toString());
    }
}
