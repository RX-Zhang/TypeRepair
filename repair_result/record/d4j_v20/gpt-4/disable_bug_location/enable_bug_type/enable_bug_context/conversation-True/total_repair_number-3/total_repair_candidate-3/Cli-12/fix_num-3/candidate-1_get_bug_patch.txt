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
            // Check for long option with '=' sign (e.g. --foo=bar)
            int equalsIndex = arg.indexOf('=');
            if (equalsIndex != -1)
            {
                String opt;
                if (arg.startsWith("--"))
                {
                    opt = arg.substring(2, equalsIndex);
                    if (options.hasOption(opt))
                    {
                        tokens.add("--" + opt);
                        tokens.add(arg.substring(equalsIndex + 1));
                        continue;
                    }
                }
                else if (arg.startsWith("-"))
                {
                    opt = arg.substring(1, equalsIndex);
                    if (opt.length() == 1 && options.hasOption(opt))
                    {
                        tokens.add("-" + opt);
                        tokens.add(arg.substring(equalsIndex + 1));
                        continue;
                    }
                }
            }

            String opt = Util.stripLeadingHyphens(arg);

            if (options.hasOption(opt))
            {
                tokens.add(arg);
            }
            else
            {
                if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
                {
                    // the format is a special properties option (-Dproperty=value)
                    tokens.add(arg.substring(0, 2)); // e.g. -D
                    tokens.add(arg.substring(2));    // property=value
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
