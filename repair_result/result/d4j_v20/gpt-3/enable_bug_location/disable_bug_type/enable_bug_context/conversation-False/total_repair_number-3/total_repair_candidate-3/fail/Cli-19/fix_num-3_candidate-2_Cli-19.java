private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else
    {
        // Unrecognized option encountered; throw exception
        throw new UnrecognizedOptionException("Unrecognized option: " + token, token);
    }
}
