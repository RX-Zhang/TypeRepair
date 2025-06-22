private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else
    {
        // When the option is unrecognized, throw an exception rather than silently consuming it
        throw new UnrecognizedOptionException("Unrecognized option: " + token, token);
    }
}
