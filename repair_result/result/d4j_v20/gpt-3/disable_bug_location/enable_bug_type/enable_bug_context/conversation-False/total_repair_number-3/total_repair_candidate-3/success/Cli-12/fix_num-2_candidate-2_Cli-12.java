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
            // Check for '=' in argument to separate option and value
            int pos = arg.indexOf('=');
            if (pos != -1)
            {
                String opt = arg.substring(0, pos);
                String value = arg.substring(pos + 1);

                String optWithoutHyphens = Util.stripLeadingHyphens(opt);

                if (options.hasOption(optWithoutHyphens))
                {
                    tokens.add(opt);
                    tokens.add(value);
                }
                else
                {
                    // Handle the case where first two chars might be an option (like -Dproperty=value)
                    if (opt.length() >= 2 && options.hasOption(opt.substring(0, 2)))
                    {
                        tokens.add(opt.substring(0, 2)); // e.g. -D
                        tokens.add(opt.substring(2) + "=" + value); // rest concatenated with '=' and the value
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
                String opt = Util.stripLeadingHyphens(arg);

                if (options.hasOption(opt))
                {
                    tokens.add(arg);
                }
                else
                {
                    if (arg.length() >= 2 && options.hasOption(arg.substring(0, 2)))
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
