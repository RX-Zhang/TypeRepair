protected String[] flatten(Options options, String[] arguments, boolean stopAtNonOption)
{
    List tokens = new ArrayList();

    boolean eatTheRest = false;

    for (int i = 0; i < arguments.length; i++)
    {
        String arg = arguments[i];

        if ("--".equals(arg))
        {
            eatTheRest = true;
            tokens.add("--");
        }
        else if ("-".equals(arg))
        {
            tokens.add("-");
        }
        else if (arg.startsWith("-"))
        {
            String opt = Util.stripLeadingHyphens(arg);

            // Check if argument contains '=' character to handle cases like --foo=bar or -f=bar
            int equalsIndex = arg.indexOf('=');
            if (equalsIndex != -1)
            {
                String optName;
                if (arg.startsWith("--"))
                {
                    optName = arg.substring(2, equalsIndex);
                    if (options.hasOption(optName))
                    {
                        tokens.add("--" + optName);
                        tokens.add(arg.substring(equalsIndex + 1));
                    }
                    else
                    {
                        tokens.add(arg);
                    }
                }
                else if (arg.startsWith("-") && arg.length() > 1)
                {
                    optName = arg.substring(1, equalsIndex);
                    // Here, optName would usually be a single character for short options
                    if (options.hasOption(optName))
                    {
                        tokens.add("-" + optName);
                        tokens.add(arg.substring(equalsIndex + 1));
                    }
                    else
                    {
                        tokens.add(arg);
                    }
                }
                else
                {
                    tokens.add(arg);
                }
            }
            else if (options.hasOption(opt))
            {
                tokens.add(arg);
            }
            else
            {
                // check if option prefix like -D is an option
                if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
                {
                    tokens.add(arg.substring(0, 2)); // -D
                    tokens.add(arg.substring(2)); // property=value
                }
                else
                {
                    eatTheRest = stopAtNonOption;
                    tokens.add(arg);
                }
            }
        }
        else
        {
            tokens.add(arg);
        }

        if (eatTheRest)
        {
            for (i++; i < arguments.length; i++)
            {
                tokens.add(arguments[i]);
            }
        }
    }

    return (String[]) tokens.toArray(new String[tokens.size()]);
}
