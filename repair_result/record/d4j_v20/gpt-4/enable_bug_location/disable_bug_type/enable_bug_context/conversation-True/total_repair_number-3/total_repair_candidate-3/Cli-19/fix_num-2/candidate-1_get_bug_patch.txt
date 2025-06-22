private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else if (stopAtNonOption)
    {
        // If the token is unrecognized and stopAtNonOption is true, throw exception
        throw new UnrecognizedOptionException(token);
    }
    else
    {
        tokens.add(token);
    }
}
