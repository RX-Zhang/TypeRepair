private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else
    {
        // Recognize unrecognized option and throw exception instead of silently continuing
        throw new UnrecognizedOptionException(token);
    }
}
