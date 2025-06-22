private void checkRequiredOptions()
    throws MissingOptionException
{
    // if there are required options that have not been processed
    if (!requiredOptions.isEmpty())
    {
        Iterator<String> iter = requiredOptions.iterator();
        StringBuilder buff = new StringBuilder();
        boolean first = true;

        // loop through the required options
        while (iter.hasNext())
        {
            if (!first) {
                buff.append(", "); // Add a comma for separation
            }
            buff.append(iter.next());
            first = false;
        }

        throw new MissingOptionException("Missing required options: " + buff.toString());
    }
}
