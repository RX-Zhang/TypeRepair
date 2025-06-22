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
            int equalsIndex = arg.indexOf('=');
            if (equalsIndex != -1)
            {
                String opt = arg.substring(2, equalsIndex);
                if (options.hasOption(opt))
                {
                    tokens.add("--" + opt);
                    tokens.add(arg.substring(equalsIndex + 1));
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
            if (arg.length() > 2 && arg.charAt(2) == '=')
            {
                char optChar = arg.charAt(1);
                String optStr = String.valueOf(optChar);
                if (options.hasOption(optStr))
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
