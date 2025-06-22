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
            // Check if arg is like "-f=bar" or "--foo=bar"
            int equalsIndex = arg.indexOf('=');
            if (equalsIndex != -1)
            {
                String optPart;
                if (arg.startsWith("--"))
                {
                    optPart = arg.substring(2, equalsIndex);
                    if (options.hasOption(optPart))
                    {
                        tokens.add("--" + optPart);
                        tokens.add(arg.substring(equalsIndex + 1));
                    }
                    else
                    {
                        tokens.add(arg);
                    }
                }
                else if (arg.startsWith("-"))
                {
                    optPart = arg.substring(1, equalsIndex);
                    if (optPart.length() == 1 && options.hasOption(optPart))
                    {
                        tokens.add("-" + optPart);
                        tokens.add(arg.substring(equalsIndex + 1));
                    }
                    else if (arg.length() >= 2 && options.hasOption(arg.substring(0, 2)))
                    {
                        // Covers special options like -Dproperty=value
                        tokens.add(arg.substring(0, 2));
                        tokens.add(arg.substring(2));
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
            else
            {
                String opt = Util.stripLeadingHyphens(arg);
                if (options.hasOption(opt))
                {
                    tokens.add(arg);
                }
                else if (arg.length() >= 2 && options.hasOption(arg.substring(0, 2)))
                {
                    tokens.add(arg.substring(0, 2));
                    tokens.add(arg.substring(2));
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
