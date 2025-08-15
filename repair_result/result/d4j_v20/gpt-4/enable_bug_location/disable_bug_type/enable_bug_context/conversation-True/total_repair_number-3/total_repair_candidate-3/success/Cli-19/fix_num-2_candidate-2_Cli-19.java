private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else if (stopAtNonOption)
    {
        if (token.length() > 2)
        {
            burstToken(token, stopAtNonOption);
        }
        else
        {
            eatTheRest = true;
            tokens.add(token);
        }
    }
    else
    {
        tokens.add(token);
    }
}
