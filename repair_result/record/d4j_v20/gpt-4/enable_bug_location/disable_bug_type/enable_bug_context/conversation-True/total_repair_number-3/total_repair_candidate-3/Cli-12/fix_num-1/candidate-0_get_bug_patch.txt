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
            int pos = arg.indexOf('=');

            if (pos != -1)
            {
                String optName = arg.substring(0, pos);
                String optValue = arg.substring(pos + 1);

                if (options.hasOption(opt))
                {
                    tokens.add(optName);
                    tokens.add(optValue);
                }
                else if (options.hasOption(optName))
                {
                    tokens.add(optName);
                    tokens.add(optValue);
                }
                else
                {
                    eatTheRest = stopAtNonOption;
                    tokens.add(arg);
                }
            }
            else if (options.hasOption(opt))
            {
                tokens.add(arg);
            }
            else if (options.hasOption(arg.substring(0, 2)))
            {
                // the format is -Dproperty=value, treat specially
                tokens.add(arg.substring(0, 2)); // -D
                tokens.add(arg.substring(2));    // property=value
            }
            else
            {
                eatTheRest = stopAtNonOption;
                tokens.add(arg);
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
