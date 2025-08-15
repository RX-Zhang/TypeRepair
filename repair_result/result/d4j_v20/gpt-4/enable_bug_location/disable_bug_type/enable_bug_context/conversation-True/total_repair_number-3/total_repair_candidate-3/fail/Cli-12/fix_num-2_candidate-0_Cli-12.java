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
            // Handle long options starting with "--" and containing '='
            if (arg.startsWith("--"))
            {
                int eqlIndex = arg.indexOf('=');
                if (eqlIndex != -1)
                {
                    String optName = arg.substring(2, eqlIndex);
                    if (options.hasOption(optName))
                    {
                        tokens.add("--" + optName);
                        tokens.add(arg.substring(eqlIndex + 1));
                        continue;
                    }
                    else
                    {
                        // no recognized option; treat as one token
                        tokens.add(arg);
                        continue;
                    }
                }
                else
                {
                    String optName = arg.substring(2);
                    if (options.hasOption(optName))
                    {
                        tokens.add(arg);
                        continue;
                    }
                    else
                    {
                        tokens.add(arg);
                        continue;
                    }
                }
            }
            // Handle short options starting with "-" (single dash)
            else if (arg.startsWith("-"))
            {
                int eqlIndex = arg.indexOf('=');
                if (eqlIndex != -1 && eqlIndex == 2)
                {
                    // option like -f=bar
                    String optName = arg.substring(1, 2);
                    if (options.hasOption(optName))
                    {
                        tokens.add("-" + optName);
                        tokens.add(arg.substring(eqlIndex + 1));
                        continue;
                    }
                    else
                    {
                        tokens.add(arg);
                        continue;
                    }
                }
                else
                {
                    String opt = Util.stripLeadingHyphens(arg);
                    if (options.hasOption(opt))
                    {
                        tokens.add(arg);
                    }
                    else
                    {
                        if (options.hasOption(arg.substring(0, 2)))
                        {
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
            }
            else
            {
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
