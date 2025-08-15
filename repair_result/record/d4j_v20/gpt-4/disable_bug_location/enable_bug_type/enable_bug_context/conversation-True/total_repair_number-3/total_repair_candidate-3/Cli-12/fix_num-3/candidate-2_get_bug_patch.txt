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

            if (options.hasOption(opt))
            {
                tokens.add(arg);
            }
            else
            {
                // Check for options with '=' in them for long and short options
                int eqIndex = arg.indexOf('=');
                if (eqIndex != -1)
                {
                    String optName;
                    if (arg.startsWith("--"))
                    {
                        // long option
                        optName = arg.substring(2, eqIndex);
                        if (options.hasOption(optName))
                        {
                            tokens.add("--" + optName);
                            tokens.add(arg.substring(eqIndex + 1));
                            continue;
                        }
                    }
                    else if (arg.startsWith("-"))
                    {
                        // short option
                        optName = arg.substring(1, eqIndex);
                        if (optName.length() == 1 && options.hasOption(optName))
                        {
                            tokens.add("-" + optName);
                            tokens.add(arg.substring(eqIndex + 1));
                            continue;
                        }
                    }
                }

                // Check if the initial part (first two chars) is a valid option (e.g., -D)
                if (arg.length() >= 2 && options.hasOption(arg.substring(0, 2)))
                {
                    tokens.add(arg.substring(0, 2)); // -D
                    if (arg.length() > 2)
                    {
                        tokens.add(arg.substring(2)); // property=value
                    }
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
