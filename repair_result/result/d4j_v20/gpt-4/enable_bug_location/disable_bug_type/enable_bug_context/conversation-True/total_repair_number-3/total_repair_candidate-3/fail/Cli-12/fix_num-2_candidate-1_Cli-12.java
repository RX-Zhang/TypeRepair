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
            // handle long options
            int eqIndex = arg.indexOf('=');
            if (eqIndex != -1)
            {
                String opt = arg.substring(2, eqIndex);
                if (options.hasOption(opt))
                {
                    tokens.add("--" + opt);
                    tokens.add(arg.substring(eqIndex + 1));
                }
                else
                {
                    eatTheRest = stopAtNonOption;
                    tokens.add(arg);
                }
            }
            else
            {
                String opt = arg.substring(2);
                if (options.hasOption(opt))
                {
                    tokens.add(arg);
                }
                else
                {
                    eatTheRest = stopAtNonOption;
                    tokens.add(arg);
                }
            }
        }
        else if (arg.startsWith("-"))
        {
            // handle short options
            if (arg.length() > 2 && arg.charAt(2) == '=')
            {
                char optChar = arg.charAt(1);
                if (options.hasOption(String.valueOf(optChar)))
                {
                    tokens.add("-" + optChar);
                    tokens.add(arg.substring(3));
                }
                else
                {
                    eatTheRest = stopAtNonOption;
                    tokens.add(arg);
                }
            }
            else
            {
                String opt = arg.substring(1);
                if (options.hasOption(opt))
                {
                    tokens.add(arg);
                }
                else if (options.hasOption(arg.substring(0,2)))
                {
                    tokens.add(arg.substring(0,2));
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
