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
            // Separate the option part and the value part on '=' if any
            String opt;
            String value = null;
            int equalsIndex = arg.indexOf('=');

            if (equalsIndex != -1)
            {
                opt = Util.stripLeadingHyphens(arg.substring(0, equalsIndex));
                value = arg.substring(equalsIndex + 1);
            }
            else
            {
                opt = Util.stripLeadingHyphens(arg);
            }

            if (options.hasOption(opt))
            {
                tokens.add(arg);
            }
            else if (equalsIndex != -1 && options.hasOption(arg.substring(0, 2)))
            {
                // the option is the first two characters (like -D)
                tokens.add(arg.substring(0, 2));
                tokens.add(arg.substring(2));
            }
            else
            {
                eatTheRest = stopAtNonOption;
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
