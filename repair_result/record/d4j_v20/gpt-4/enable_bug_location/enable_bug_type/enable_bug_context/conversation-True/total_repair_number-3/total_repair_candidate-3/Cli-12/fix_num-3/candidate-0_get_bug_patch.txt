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
            String opt = Util.stripLeadingHyphens(arg);
            int pos = opt.indexOf('=');

            String optName = (pos == -1) ? opt : opt.substring(0, pos);

            if (options.hasOption(optName))
            {
                if (pos == -1)
                {
                    tokens.add(arg);
                }
                else
                {
                    tokens.add(arg.substring(0, arg.indexOf('=') ));
                    tokens.add(arg.substring(arg.indexOf('=') + 1));
                }
            }
            else
            {
                if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
                {
                    // the format is --foo=value or -Dproperty=value
                    tokens.add(arg.substring(0, 2)); // -D or --
                    tokens.add(arg.substring(2)); // property=value or foo=value
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
