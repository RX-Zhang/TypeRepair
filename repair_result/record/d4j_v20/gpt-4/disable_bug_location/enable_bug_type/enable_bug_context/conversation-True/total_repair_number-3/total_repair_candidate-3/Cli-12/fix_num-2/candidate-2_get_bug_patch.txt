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
            // Handle long options with '=' sign
            int equalsIndex = arg.indexOf('=');
            String opt;
            if (equalsIndex != -1)
            {
                opt = arg.substring(2, equalsIndex);
                if (options.hasOption(opt))
                {
                    tokens.add("--" + opt); // add the option
                    tokens.add(arg.substring(equalsIndex + 1)); // add the argument value
                }
                else
                {
                    tokens.add(arg);
                }
            }
            else
            {
                opt = arg.substring(2);
                if (options.hasOption(opt))
                {
                    tokens.add(arg);
                }
                else
                {
                    tokens.add(arg);
                }
            }
        }
        else if (arg.startsWith("-"))
        {
            // Handle short options, possibly with '=' sign
            if (arg.length() > 2 && arg.charAt(2) == '=')
            {
                char optChar = arg.charAt(1);
                String opt = String.valueOf(optChar);
                if (options.hasOption(opt))
                {
                    tokens.add("-" + opt);
                    tokens.add(arg.substring(3));
                }
                else
                {
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
