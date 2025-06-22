private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
    }
    else
    {
        // Since the option is unrecognized, throw an exception or set eatTheRest accordingly
        if (stopAtNonOption)
        {
            eatTheRest = true;
            tokens.add(token);
        }
        else
        {
            // This case should result in an error for unrecognized option
            throw new UnrecognizedOptionException("Unrecognized option: " + token, token);
        }
    }
}
