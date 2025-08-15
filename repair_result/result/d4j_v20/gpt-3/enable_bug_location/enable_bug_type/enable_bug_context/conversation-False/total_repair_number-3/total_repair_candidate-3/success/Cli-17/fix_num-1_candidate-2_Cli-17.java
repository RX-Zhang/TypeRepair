protected void burstToken(String token, boolean stopAtNonOption)
{
    for (int i = 1; i < token.length(); i++)
    {
        String ch = String.valueOf(token.charAt(i));

        if (options.hasOption(ch))
        {
            tokens.add("-" + ch);
            currentOption = options.getOption(ch);

            if (currentOption.hasArg() && (i + 1 < token.length()))
            {
                tokens.add(token.substring(i + 1));
                break;
            }
        }
        else if (stopAtNonOption)
        {
            process(token.substring(i));
            eatTheRest = true; // Set eatTheRest to true to indicate non-option encountered
            break; // Exit the loop after processing non-option
        }
        else
        {
            tokens.add(token); // Added this line to handle cases where we just want to add the token
            break; // Exit the loop
        }
    }
}
