protected String[] flatten(Options options, String[] arguments, boolean stopAtNonOption)
{
    init();
    this.options = options;

    // an iterator for the command line tokens
    Iterator iter = Arrays.asList(arguments).iterator();

    // process each command line token
    while (iter.hasNext())
    {
        // get the next command line token
        String token = (String) iter.next();

        // handle long option --foo or --foo=bar
        if (token.startsWith("--"))
        {
            if (token.indexOf('=') != -1)
            {
                tokens.add(token.substring(0, token.indexOf('=')));
                tokens.add(token.substring(token.indexOf('=') + 1, token.length()));
            }
            else
            {
                tokens.add(token);
            }
        }

        // single hyphen
        else if ("-".equals(token))
        {
            tokens.add(token);
        }
        else if (token.startsWith("-"))
        {
            if (options.hasOption(token))
            {
                tokens.add(token);
                currentOption = options.getOption(token);
            }
            else if (token.length() == 2)
            {
                processOptionToken(token, stopAtNonOption);
            }
            // requires bursting
            else
            {
                burstToken(token, stopAtNonOption);
            }
        }
        else if (stopAtNonOption)
        {
            process(token);
        }
        else
        {
            tokens.add(token);
        }

        gobble(iter);
    }

    return tokens.toArray(new String[0]);
}
