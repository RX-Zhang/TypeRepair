private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else
    {
        // Since the option is unrecognized, throw an exception instead of silently accepting it.
        throw new UnrecognizedOptionException(token);
    }
}
