private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else
    {
        // If token is not an option and stopAtNonOption is true,
        // set eatTheRest to true and throw an UnrecognizedOptionException.
        if (stopAtNonOption)
        {
            eatTheRest = true;
            throw new UnrecognizedOptionException("Unrecognized option: " + token, token);
        }
        else
        {
            tokens.add(token);
        }
    }
}
