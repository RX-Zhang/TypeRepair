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
            // Handle arguments with '=' such as --foo=bar or -f=bar
            int pos = arg.indexOf('=');
            String opt;
            if (pos != -1)
            {
                opt = Util.stripLeadingHyphens(arg.substring(0, pos));
                if (options.hasOption(opt))
                {
                    tokens.add(arg.substring(0, pos));
                    tokens.add(arg.substring(pos + 1));
                    continue;
                }
            }

            // No '=' or option not found with '=' format, proceed as usual
            opt = Util.stripLeadingHyphens(arg);
            if (options.hasOption(opt))
            {
                tokens.add(arg);
            }
            else
            {
                if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
                {
                    // the format is a special properties option (-Dproperty=value)
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
