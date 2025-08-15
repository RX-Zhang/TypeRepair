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
            int equalIndex = arg.indexOf('=');
            if (equalIndex != -1)
            {
                String opt = Util.stripLeadingHyphens(arg.substring(0, equalIndex));
                if (options.hasOption(opt))
                {
                    tokens.add(arg.substring(0, equalIndex));
                    tokens.add(arg.substring(equalIndex + 1));
                }
                else if (options.hasOption(arg.substring(0, 2)))
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
            else
            {
                String opt = Util.stripLeadingHyphens(arg);
                if (options.hasOption(opt))
                {
                    tokens.add(arg);
                }
                else if (options.hasOption(arg.substring(0, 2)))
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
