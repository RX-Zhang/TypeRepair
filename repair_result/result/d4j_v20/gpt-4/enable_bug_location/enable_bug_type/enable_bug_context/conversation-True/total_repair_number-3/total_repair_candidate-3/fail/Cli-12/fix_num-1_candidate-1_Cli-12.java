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
        else if (arg.startsWith("--"))
        {
            // handle long options with -- prefix
            int equalsIndex = arg.indexOf('=');
            if (equalsIndex != -1)
            {
                String opt = arg.substring(2, equalsIndex);
                String value = arg.substring(equalsIndex + 1);
                if (options.hasOption(opt))
                {
                    tokens.add("--" + opt);
                    tokens.add(value);
                }
                else
                {
                    tokens.add(arg);
                }
            }
            else if (options.hasOption(arg.substring(2)))
            {
                tokens.add(arg);
            }
            else
            {
                eatTheRest = stopAtNonOption;
                tokens.add(arg);
            }
        }
        else if (arg.startsWith("-"))
        {
            // handle short options with - prefix
            int equalsIndex = arg.indexOf('=');
            if (equalsIndex != -1)
            {
                String opt = arg.substring(1, equalsIndex);
                String value = arg.substring(equalsIndex + 1);
                if (opt.length() == 1 && options.hasOption(opt))
                {
                    tokens.add("-" + opt);
                    tokens.add(value);
                }
                else
                {
                    // Possibly a special property option like -Dproperty=value
                    if (options.hasOption(arg.substring(0, 2)))
                    {
                        tokens.add(arg.substring(0, 2)); // -D
                        tokens.add(arg.substring(2));    // property=value
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
                String opt = arg.substring(1);
                if (options.hasOption(opt))
                {
                    tokens.add(arg);
                }
                else
                {
                    if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
                    {
                        tokens.add(arg.substring(0, 2)); // e.g. -D
                        tokens.add(arg.substring(2)); // rest
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
