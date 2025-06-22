private void checkRequiredOptions()
    throws MissingOptionException
{
    // if there are required options that have not been
    // processed
    if (requiredOptions.size() > 0)
    {
        Iterator iter = requiredOptions.iterator();
        StringBuffer buff = new StringBuffer();

        // loop through the required options
        while (iter.hasNext())
        {
            buff.append(iter.next());
        }

        String message;
        if (requiredOptions.size() > 1) {
            message = "Missing required options: " + buff.toString();
        } else {
            message = "Missing required option: " + buff.toString();
        }

        throw new MissingOptionException(message);
    }
}
