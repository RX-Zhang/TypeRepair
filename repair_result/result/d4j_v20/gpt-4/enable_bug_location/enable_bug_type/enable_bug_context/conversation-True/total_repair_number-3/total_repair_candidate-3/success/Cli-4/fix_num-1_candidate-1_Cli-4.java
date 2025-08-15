private void checkRequiredOptions()
    throws MissingOptionException
{
    int size = requiredOptions.size();

    if (size > 0)
    {
        Iterator iter = requiredOptions.iterator();
        StringBuffer buff = new StringBuffer();

        if (size > 1)
        {
            buff.append("Missing required options: ");
        }
        else
        {
            buff.append("Missing required option: ");
        }

        while (iter.hasNext())
        {
            buff.append(iter.next());
        }

        throw new MissingOptionException(buff.toString());
    }
}
