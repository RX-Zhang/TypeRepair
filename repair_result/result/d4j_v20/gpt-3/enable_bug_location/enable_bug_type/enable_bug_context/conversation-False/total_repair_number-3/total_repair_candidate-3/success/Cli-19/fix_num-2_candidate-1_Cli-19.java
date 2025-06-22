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
            // If it's not an option and we are not stopping at non-option, add the token directly
            tokens.add(token);
        }
    }
}
