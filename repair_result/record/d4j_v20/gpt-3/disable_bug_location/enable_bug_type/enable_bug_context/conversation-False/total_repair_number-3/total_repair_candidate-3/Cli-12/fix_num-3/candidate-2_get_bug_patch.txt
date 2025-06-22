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
            // handle --foo=bar or -f=bar cases
            int equalIndex = arg.indexOf('=');
            String opt = null;
            String optArg = null;

            if (equalIndex != -1)
            {
                opt = arg.substring(0, equalIndex);
                optArg = arg.substring(equalIndex + 1);
            }
            else
            {
                opt = arg;
            }

            String optName = Util.stripLeadingHyphens(opt);

            if (options.hasOption(optName))
            {
                tokens.add(opt);
                if (optArg != null)
                {
                    tokens.add(optArg);
                }
            }
            else
            {
                // Try checking if first 2 chars is an option (for e.g. -Dproperty=value)
                if (opt.length() > 1 && options.hasOption(opt.substring(0, 2)))
                {
                    tokens.add(opt.substring(0, 2)); // -D
                    if (opt.length() > 2)
                    {
                        tokens.add(opt.substring(2));
                    }
                    if (optArg != null)
                    {
                        tokens.add(optArg);
                    }
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
