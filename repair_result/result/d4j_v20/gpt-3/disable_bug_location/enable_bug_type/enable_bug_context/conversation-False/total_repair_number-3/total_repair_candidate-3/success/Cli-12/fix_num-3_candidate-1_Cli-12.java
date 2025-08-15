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
            // Check for --foo=bar or -f=bar style arguments
            int indexOfEqual = arg.indexOf('=');
            if (indexOfEqual != -1)
            {
                String optPart = null;
                if (arg.startsWith("--"))
                {
                    optPart = arg.substring(2, indexOfEqual);
                    if (options.hasOption(optPart))
                    {
                        tokens.add("--" + optPart);
                        tokens.add(arg.substring(indexOfEqual + 1));
                        continue;
                    }
                }
                else if (arg.startsWith("-"))
                {
                    optPart = arg.substring(1, indexOfEqual);
                    if (options.hasOption(optPart))
                    {
                        tokens.add("-" + optPart);
                        tokens.add(arg.substring(indexOfEqual + 1));
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
