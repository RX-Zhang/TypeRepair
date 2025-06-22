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
            String opt = arg;
            int pos = arg.indexOf('=');

            if (pos != -1)
            {
                opt = arg.substring(0, pos);
            }

            String strippedOpt = Util.stripLeadingHyphens(opt);

            if (options.hasOption(strippedOpt))
            {
                if (pos != -1)
                {
                    tokens.add(opt);
                    tokens.add(arg.substring(pos + 1));
                }
                else
                {
                    tokens.add(arg);
                }
            }
            else if (arg.length() > 1 && options.hasOption(arg.substring(0,2)))
            {
                // the format is --foo=value or -foo=value
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
