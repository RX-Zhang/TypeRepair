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
            int pos = arg.indexOf('=');
            String opt;
            if (pos != -1)
            {
                opt = Util.stripLeadingHyphens(arg.substring(0, pos));
            }
            else
            {
                opt = Util.stripLeadingHyphens(arg);
            }

            if (options.hasOption(opt))
            {
                if (pos != -1)
                {
                    // option with '=' sign, split into option and value
                    tokens.add(arg.substring(0, pos));
                    tokens.add(arg.substring(pos + 1));
                }
                else
                {
                    tokens.add(arg);
                }
            }
            else if (arg.startsWith("--") && pos != -1)
            {
                // long option with '=' sign but no matching option, treat as a single token
                tokens.add(arg);
            }
            else
            {
                if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
                {
                    // special properties option like -Dproperty=value
                    tokens.add(arg.substring(0, 2)); // e.g. -D
                    tokens.add(arg.substring(2));    // rest after -D
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
