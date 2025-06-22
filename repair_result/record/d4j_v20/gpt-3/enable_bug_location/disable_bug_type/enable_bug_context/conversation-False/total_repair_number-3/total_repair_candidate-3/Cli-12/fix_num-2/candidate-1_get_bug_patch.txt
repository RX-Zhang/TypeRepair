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
            // Handle options with an '=' sign, e.g. --foo=bar or -f=bar
            int pos = arg.indexOf('=');
            String opt = null;
            String value = null;
            if (pos != -1)
            {
                opt = arg.substring(0, pos);
                value = arg.substring(pos + 1);

                String strippedOpt = Util.stripLeadingHyphens(opt);

                if (options.hasOption(strippedOpt))
                {
                    tokens.add(opt);
                    tokens.add(value);
                }
                else
                {
                    // Not a recognized option, treat as a whole argument
                    tokens.add(arg);
                }
            }
            else
            {
                String stripped = Util.stripLeadingHyphens(arg);

                if (options.hasOption(stripped))
                {
                    tokens.add(arg);
                }
                else if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
                {
                    // special properties option (e.g. -Dproperty=value)
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
