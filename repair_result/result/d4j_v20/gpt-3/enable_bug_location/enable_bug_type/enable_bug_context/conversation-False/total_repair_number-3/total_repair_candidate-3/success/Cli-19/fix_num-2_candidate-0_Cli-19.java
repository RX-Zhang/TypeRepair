private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else
    {
        if (stopAtNonOption)
        {
            eatTheRest = true;
            tokens.add(token);
        }
        else
        {
            // If not stopping at non-option, we should add the token as is
            tokens.add(token);
        }
    }
}
