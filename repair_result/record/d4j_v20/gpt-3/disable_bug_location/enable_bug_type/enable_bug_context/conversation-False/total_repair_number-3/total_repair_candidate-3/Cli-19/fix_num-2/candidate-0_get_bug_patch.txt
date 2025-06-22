private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else if (stopAtNonOption)
    {
        // throw exception for unrecognized option instead of silently accepting it
        throw new UnrecognizedOptionException("Unrecognized option: " + token, token);
    }
    else
    {
        eatTheRest = true;
        tokens.add(token);
    }
}
