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
            else if (("yes".equalsIgnoreCase(value)
                    || "true".equalsIgnoreCase(value)
                    || "1".equalsIgnoreCase(value)))
            {
                // if the value is yes, true or 1 then add the option to the CommandLine
                cmd.addOption(opt);
            }
            // if value is neither yes/true/1 nor has argument, do not add the option
        }
    }
}
