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
            // Check for tokens with '=' sign, e.g. --foo=bar or -f=bar
            int equalsIndex = arg.indexOf('=');

            if (equalsIndex != -1)
            {
                String opt = arg.substring(0, equalsIndex);
                String value = arg.substring(equalsIndex + 1);

                String optClean = Util.stripLeadingHyphens(opt);

                if (options.hasOption(optClean))
                {
                    // Add the option and separate the value as the next token
                    tokens.add(opt);
                    tokens.add(value);
                }
                else if (options.hasOption(opt))
                {
                    // Defensive fallback, if the option with hyphens exists
                    tokens.add(opt);
                    tokens.add(value);
                }
                else if (opt.length() == 2 && options.hasOption(opt))
                {
                    // For special case like -Dproperty=value
                    tokens.add(opt);
                    tokens.add(value);
                }
                else
                {
                    eatTheRest = stopAtNonOption;
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
                    tokens.add(arg.substring(0, 2)); // e.g. -D
                    tokens.add(arg.substring(2));    // rest
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
