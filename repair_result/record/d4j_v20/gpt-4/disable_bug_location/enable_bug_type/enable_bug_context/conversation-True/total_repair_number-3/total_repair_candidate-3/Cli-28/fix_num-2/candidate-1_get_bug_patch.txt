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
                    }
                    catch (RuntimeException exp)
                    {
                        // if we cannot add the value don't worry about it
                    }
                }
            }
            else if ("yes".equalsIgnoreCase(value)
                    || "true".equalsIgnoreCase(value)
                    || "1".equalsIgnoreCase(value))
            {
                cmd.addOption(opt);
            }
            // If the value is not yes, true, or 1 and the option does not have an argument,
            // we do NOT add the option and just continue to the next property
            else
            {
                continue;
            }

            // Add the option if it has arguments (already handled above) or if the value is affirmative (handled above)
            if (opt.hasArg())
            {
                cmd.addOption(opt);
            }
        }
    }
}
