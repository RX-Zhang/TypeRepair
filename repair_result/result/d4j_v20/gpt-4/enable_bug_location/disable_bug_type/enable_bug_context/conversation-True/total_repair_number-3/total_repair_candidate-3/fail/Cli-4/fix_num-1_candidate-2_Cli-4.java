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
                buff.append(", "); // add a comma for separation
            }
            buff.append(iter.next());
            first = false;
        }

        // Determine the correct exception message
        String message = requiredOptions.size() > 1 ? 
            "Missing required options: " : 
            "Missing required option: ";
        
        throw new MissingOptionException(message + buff.toString());
    }
}
