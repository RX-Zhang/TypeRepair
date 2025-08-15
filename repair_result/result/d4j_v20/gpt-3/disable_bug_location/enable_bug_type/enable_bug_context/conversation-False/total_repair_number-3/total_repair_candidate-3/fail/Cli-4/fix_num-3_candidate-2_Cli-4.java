private void checkRequiredOptions()
    throws MissingOptionException
{
    // if there are required options that have not been
    // processed
    if (!requiredOptions.isEmpty())
    {
        Iterator<String> iter = requiredOptions.iterator();
        StringBuilder buff = new StringBuilder();
        
        // loop through the required options
        while (iter.hasNext())
        {
            buff.append(iter.next());
            if (iter.hasNext()) {
                buff.append(", "); // Add a comma for separation
            }
        }

        String message = requiredOptions.size() > 1 ? 
            "Missing required options: " + buff.toString() : 
            "Missing required option: " + buff.toString();
        
        throw new MissingOptionException(message);
    }
}
