private void checkRequiredOptions()
    throws MissingOptionException
{
    // if there are required options that have not been processed
    if (!requiredOptions.isEmpty())
    {
        Iterator iter = requiredOptions.iterator();
        StringBuffer buff = new StringBuffer();
        buff.append("Missing required options: "); // Add the message prefix

        // loop through the required options
        while (iter.hasNext())
        {
            buff.append(iter.next());
        }

        throw new MissingOptionException(buff.toString());
    }
}
