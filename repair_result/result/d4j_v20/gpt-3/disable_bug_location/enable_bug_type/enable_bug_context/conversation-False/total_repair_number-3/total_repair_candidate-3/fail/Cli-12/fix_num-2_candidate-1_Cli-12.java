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
            // find the position of '=' if any
            int equalPos = arg.indexOf('=');

            if (equalPos != -1)
            {
                String optName = arg.substring(0, equalPos);
                String optValue = arg.substring(equalPos + 1);

                // strip leading hyphens from optName for option checking
                String optNameStripped = Util.stripLeadingHyphens(optName);

                if (options.hasOption(optNameStripped))
                {
                    tokens.add(optName);
                    tokens.add(optValue);
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
                else
                {
                    // Check if the first two characters (e.g. "-D") is an option
                    if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
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
