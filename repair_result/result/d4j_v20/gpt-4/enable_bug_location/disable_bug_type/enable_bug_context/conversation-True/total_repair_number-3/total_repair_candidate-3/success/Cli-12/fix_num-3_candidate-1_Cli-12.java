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
            String optName;
            String optValue = null;

            // Check for '=' and split option and value
            int pos = arg.indexOf('=');
            if (pos != -1)
            {
                // Separate option and value part
                optName = arg.substring(0, pos);
                optValue = arg.substring(pos + 1);
            }
            else
            {
                optName = arg;
            }

            String strippedOptName = Util.stripLeadingHyphens(optName);

            if (options.hasOption(strippedOptName))
            {
                tokens.add(optName);
                if (optValue != null)
                {
                    tokens.add(optValue);
                }
            }
            else if (optName.length() > 2 && options.hasOption(optName.substring(0, 2)))
            {
                // the format is a special properties option (-Dproperty=value)
                tokens.add(optName.substring(0, 2)); // e.g. -D
                tokens.add(optName.substring(2) + (optValue != null ? "=" + optValue : ""));
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
