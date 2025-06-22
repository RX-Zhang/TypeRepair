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
            // handle options with '=' sign, e.g. --foo=bar, -f=bar, -Dproperty=value
            int equalsIndex = arg.indexOf('=');
            if (equalsIndex != -1)
            {
                String opt = arg.substring(0, equalsIndex);
                String value = arg.substring(equalsIndex + 1);

                // strip leading hyphens from opt for lookup
                String optStripped = Util.stripLeadingHyphens(opt);

                if (options.hasOption(optStripped))
                {
                    tokens.add(opt);
                    tokens.add(value);
                }
                else if (opt.length() == 2 && options.hasOption(opt))  // like -Dproperty=value, treat -D specially
                {
                    tokens.add(opt);
                    tokens.add(value);
                }
                else
                {
                    // unrecognized option with '=' sign, treat whole as one token
                    eatTheRest = stopAtNonOption;
                    tokens.add(arg);
                }
            }
            else
            {
                String optStripped = Util.stripLeadingHyphens(arg);
                if (options.hasOption(optStripped))
                {
                    tokens.add(arg);
                }
                else if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
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
