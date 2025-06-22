protected String[] flatten(Options options, String[] arguments, boolean stopAtNonOption)
{
    init();
    this.options = options;

    // an iterator for the command line tokens
    Iterator<String> iter = Arrays.asList(arguments).iterator();

    // process each command line token
    while (iter.hasNext())
    {
        // get the next command line token
        String token = iter.next();

        // handle long option --foo or --foo=bar
        if (token.startsWith("--"))
        {
            if (token.indexOf('=') != -1)
            {
                tokens.add(token.substring(0, token.indexOf('=')));
                tokens.add(token.substring(token.indexOf('=') + 1));
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
            if (token.length() == 2)
            {
                processOptionToken(token, stopAtNonOption);
            }
            else if (options.hasOption(token))
            {
                tokens.add(token);
                currentOption = options.getOption(token); // Ensure currentOption is set to handle its args
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

    return tokens.toArray(new String[0]); // Use the preferred way to convert a List to an array
}
