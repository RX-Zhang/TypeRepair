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
            int equalsIndex = arg.indexOf("=");
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
            else
            {
                tokens.add(arg);
            }
        }
        else if (arg.startsWith("-"))
        {
            int equalsIndex = arg.indexOf("=");
            if (equalsIndex != -1)
            {
                String opt = arg.substring(1, equalsIndex);
                String value = arg.substring(equalsIndex + 1);
                if (options.hasOption(opt))
                {
                    tokens.add("-" + opt);
                    tokens.add(value);
                }
                else if (options.hasOption(arg.substring(0, 2)))
                {
                    // the format is a special properties option (-Dproperty=value)
                    tokens.add(arg.substring(0, 2)); // -D
                    tokens.add(arg.substring(2));
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
                else
                {
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
