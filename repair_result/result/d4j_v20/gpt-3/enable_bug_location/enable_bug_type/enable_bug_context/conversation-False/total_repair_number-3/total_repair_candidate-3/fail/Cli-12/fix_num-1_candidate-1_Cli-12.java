protected String[] flatten(Options options, String[] arguments, boolean stopAtNonOption)
{
    List<String> tokens = new ArrayList<>();

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

            if (options.hasOption(opt))
            {
                tokens.add(arg);
            }
            else
            {
                if (opt.contains("=") || options.hasOption(arg.substring(0, 2)))
                {
                    // the format is --foo=value or -foo=value
                    // the format is a special properties option (-Dproperty=value)
                    String[] parts = arg.split("=", 2);
                    tokens.add(parts[0]); // -D or -foo
                    if (parts.length > 1) {
                        tokens.add(parts[1]); // property=value
                    }
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

    return tokens.toArray(new String[0]);
}
