protected void burstToken(String token, boolean stopAtNonOption)
{
    for (int i = 1; i < token.length(); i++)
    {
        String ch = String.valueOf(token.charAt(i));

        if (options.hasOption(ch))
        {
            tokens.add("-" + ch);
            currentOption = options.getOption(ch);

            if (currentOption.hasArg() && (token.length() == (i + 1)))
            {
                // Changed the condition to '==' to correctly check for the last character
                break;
            }
            else if (currentOption.hasArg())
            {
                tokens.add(token.substring(i + 1));
                break;
            }
        }
        else if (stopAtNonOption)
        {
            process(token.substring(i));
            break; // Added break here to prevent further processing of non-options
        }
        else
        {
            tokens.add(token);
            break;
        }
    }
}
