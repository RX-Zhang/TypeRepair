protected void processProperties(Properties properties)
{
    if (properties == null)
    {
        return;
    }

    for (Enumeration e = properties.propertyNames(); e.hasMoreElements();)
    {
        String option = e.nextElement().toString();

        if (!cmd.hasOption(option))
        {
            Option opt = getOptions().getOption(option);

            // get the value from the properties instance
            String value = properties.getProperty(option);

            if (opt.hasArg())
            {
                if (opt.getValues() == null || opt.getValues().length == 0)
                {
                    try
                    {
                        opt.addValueForProcessing(value);
                        cmd.addOption(opt);
                    }
                    catch (RuntimeException exp)
                    {
                        // if we cannot add the value don't worry about it
                    }
                }
                else
                {
                    // already has values, still add it
                    cmd.addOption(opt);
                }
            }
            else if ("yes".equalsIgnoreCase(value)
                    || "true".equalsIgnoreCase(value)
                    || "1".equalsIgnoreCase(value))
            {
                // add only if the value is yes, true or 1
                cmd.addOption(opt);
            }
            // else do nothing: don't add option if value is not true/yes/1
        }
    }
}
