private void processOptionToken(String token, boolean stopAtNonOption)
{
    if (options.hasOption(token))
    {
        currentOption = options.getOption(token);
        tokens.add(token);
        
        // Check if the option requires an argument
        if (currentOption.hasArg())
        {
            eatTheRest = false; // Do not eat the rest if we expect an argument
        }
    }
    else if (stopAtNonOption)
    {
        eatTheRest = true;
        tokens.add(token);
    }
}
