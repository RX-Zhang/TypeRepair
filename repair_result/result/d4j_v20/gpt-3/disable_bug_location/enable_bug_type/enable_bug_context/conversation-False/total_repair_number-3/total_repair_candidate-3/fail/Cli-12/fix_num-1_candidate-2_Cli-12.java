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
            // If the argument contains '=', handle --foo=bar or -f=bar cases
            int pos = arg.indexOf('=');

            if (pos != -1)
            {
                String optPart = arg.substring(0, pos);
                String valuePart = arg.substring(pos + 1);

                // Strip leading hyphens to get option name
                String opt = Util.stripLeadingHyphens(optPart);

                if (options.hasOption(opt))
                {
                    // Add option and its value separately
                    tokens.add(optPart);
                    tokens.add(valuePart);
                }
                else if (options.hasOption(optPart))
                {
                    // If option with hyphens exists (rare), handle it
                    tokens.add(optPart);
                    tokens.add(valuePart);
                }
                else
                {
                    // Not a recognized option, treat entire arg as a token
                    tokens.add(arg);
                    if (stopAtNonOption)
                    {
                        eatTheRest = true;
                    }
                }
            }
            else
            {
                // No '=' present; check if the stripped option exists
                String opt = Util.stripLeadingHyphens(arg);

                if (options.hasOption(opt))
                {
                    tokens.add(arg);
                }
                else if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
                {
                    // Handling special cases like -Dproperty=value (no '=' here, but arg.length > 2)
                    tokens.add(arg.substring(0, 2)); // e.g. -D
                    tokens.add(arg.substring(2));    // e.g. property=value
                }
                else
                {
                    // Unrecognized option
                    tokens.add(arg);
                    if (stopAtNonOption)
                    {
                        eatTheRest = true;
                    }
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
