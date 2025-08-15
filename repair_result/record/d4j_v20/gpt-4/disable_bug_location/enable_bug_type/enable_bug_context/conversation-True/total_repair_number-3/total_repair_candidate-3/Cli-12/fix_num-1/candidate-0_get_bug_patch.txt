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
            // Extract the option part before '=' if present
            String opt;
            int equalsIndex = arg.indexOf('=');
            if (equalsIndex != -1)
            {
                opt = Util.stripLeadingHyphens(arg.substring(0, equalsIndex));
            }
            else
            {
                opt = Util.stripLeadingHyphens(arg);
            }

            if (options.hasOption(opt))
            {
                // If argument contains '=', split it into option and value tokens
                if (equalsIndex != -1)
                {
                    tokens.add(arg.substring(0, equalsIndex)); // option with leading hyphens
                    tokens.add(arg.substring(equalsIndex + 1)); // value part
                }
                else
                {
                    tokens.add(arg);
                }
            }
            else
            {
                // Check if the first two chars (hyphen + letter) form an option
                if (arg.length() > 2 && options.hasOption(arg.substring(0, 2)))
                {
                    tokens.add(arg.substring(0, 2)); // e.g. -D
                    tokens.add(arg.substring(2));    // rest of the string
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
            break;
        }
    }

    return (String[]) tokens.toArray(new String[tokens.size()]);
}
