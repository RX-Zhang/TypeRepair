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
            int pos = arg.indexOf('=');
            String opt = null;
            String value = null;

            if (pos != -1)
            {
                String optString = arg.substring(0, pos);
                opt = Util.stripLeadingHyphens(optString);
                value = arg.substring(pos + 1);

                if (options.hasOption(opt))
                {
                    tokens.add(optString);
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
                    eatTheRest = stopAtNonOption;
                    tokens.add(arg);
                }
            }
            else
            {
                opt = Util.stripLeadingHyphens(arg);

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
