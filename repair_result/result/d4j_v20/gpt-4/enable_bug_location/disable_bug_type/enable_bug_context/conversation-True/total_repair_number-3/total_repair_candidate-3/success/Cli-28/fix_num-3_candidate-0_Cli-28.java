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
                // add the option to the CommandLine only if the value is yes, true or 1
                cmd.addOption(opt);
                continue;
            }
            else
            {
                // if the value is not yes, true or 1 then don't add the option to the CommandLine
                continue;
            }

            cmd.addOption(opt);
        }
    }
}
