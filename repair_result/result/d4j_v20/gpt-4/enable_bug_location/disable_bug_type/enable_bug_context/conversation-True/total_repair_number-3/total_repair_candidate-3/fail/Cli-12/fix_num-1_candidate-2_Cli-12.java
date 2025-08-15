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
            
            // Check for --option=value or -o=value
            int equalsIndex = arg.indexOf('=');
            if (equalsIndex != -1)
            {
                String optionPart = arg.substring(0, equalsIndex);
                String valuePart = arg.substring(equalsIndex + 1);

                // Handle single dash options with long option name, e.g. -foo=bar
                if (optionPart.startsWith("--"))
                {
                    String longOpt = optionPart.substring(2);
                    if (options.hasLongOption(longOpt))
                    {
                        tokens.add(optionPart);
                        tokens.add(valuePart);
                    }
                    else
                    {
                        tokens.add(arg);
                    }
                }
                else if (optionPart.startsWith("-"))
                {
                    String optKey = optionPart.substring(1);
                    // If optionKey length == 1, it's short option
                    if (optKey.length() == 1)
                    {
                        if (options.hasOption(optKey))
                        {
                            tokens.add(optionPart);
                            tokens.add(valuePart);
                        }
                        else
                        {
                            tokens.add(arg);
                        }
                    }
                    else
                    {
                        // It's a single dash long option (-foo=bar)
                        if (options.hasOption(optKey))
                        {
                            tokens.add(optionPart);
                            tokens.add(valuePart);
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
            }
            else if (options.hasOption(opt))
            {
                tokens.add(arg);
            }
            else
            {
                if (arg.length() > 1 && options.hasOption(arg.substring(0, 2)))
                {
                    // the format is a special properties option (-Dproperty=value)
                    tokens.add(arg.substring(0, 2)); // -D
                    tokens.add(arg.substring(2)); // property=value
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
