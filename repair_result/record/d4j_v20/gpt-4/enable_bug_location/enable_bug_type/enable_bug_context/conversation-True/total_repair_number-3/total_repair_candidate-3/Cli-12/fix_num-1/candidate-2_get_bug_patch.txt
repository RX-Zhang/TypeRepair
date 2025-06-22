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

            // Check for options with '=' sign
            if (arg.startsWith("--"))
            {
                int pos = arg.indexOf('=');
                if (pos != -1)
                {
                    String optName = arg.substring(2, pos);
                    if (options.hasOption(optName))
                    {
                        tokens.add("--" + optName);
                        tokens.add(arg.substring(pos + 1));
                    }
                    else
                    {
                        eatTheRest = stopAtNonOption;
                        tokens.add(arg);
                    }
                    continue;
                }
            }
            else if (arg.startsWith("-") && !arg.startsWith("--"))
            {
                int pos = arg.indexOf('=');
                if (pos != -1)
                {
                    String optName = arg.substring(1, pos);
                    if (options.hasOption(optName))
                    {
                        tokens.add("-" + optName);
                        tokens.add(arg.substring(pos + 1));
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
                    continue;
                }
            }

            if (options.hasOption(opt))
            {
                tokens.add(arg);
            }
            else
            {
                if (options.hasOption(arg.substring(0, 2)))
                {
                    // the format is -Dproperty=value
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
