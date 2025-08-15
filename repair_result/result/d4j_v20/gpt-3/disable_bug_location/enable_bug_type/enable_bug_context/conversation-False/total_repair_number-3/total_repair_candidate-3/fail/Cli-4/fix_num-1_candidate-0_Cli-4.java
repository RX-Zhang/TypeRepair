private void checkRequiredOptions()
    throws MissingOptionException
{
    // if there are required options that have not been
    // processed
    if (!requiredOptions.isEmpty())
    {
        Iterator iter = requiredOptions.iterator();
        StringBuffer buff = new StringBuffer();
        buff.append("Missing required option");

        // loop through the required options
        while (iter.hasNext())
        {
            buff.append(": ").append(iter.next());
            if (iter.hasNext()) {
                buff.append(", ");
            }
        }

        throw new MissingOptionException(buff.toString());
    }
}
