private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else if (stopAtNonOption)
    {
        throw new UnrecognizedOptionException(token);
    }
    else
    {
        eatTheRest = true;
        tokens.add(token);
    }
}
