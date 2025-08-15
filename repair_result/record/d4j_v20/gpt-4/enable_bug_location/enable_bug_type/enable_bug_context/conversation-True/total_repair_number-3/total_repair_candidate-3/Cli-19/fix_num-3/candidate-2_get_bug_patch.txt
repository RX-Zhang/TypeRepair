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
            // Throw exception for unrecognized option here
            throw new UnrecognizedOptionException("Unrecognized option: " + token, token);
        }
    }
}
