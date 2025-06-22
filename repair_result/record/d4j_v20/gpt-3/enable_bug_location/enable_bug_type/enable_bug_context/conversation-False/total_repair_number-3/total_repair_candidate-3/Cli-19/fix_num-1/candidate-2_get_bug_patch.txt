private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
        
        if (currentOption.hasArg() || currentOption.hasArgs())
        {
            // If the option has arguments, we may need to process further
            eatTheRest = false; // Resetting eatTheRest if we are processing a valid option
        }
    }
    else if (stopAtNonOption)
    {
        eatTheRest = true;
        tokens.add(token);
    }
}
