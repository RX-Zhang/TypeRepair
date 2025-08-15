protected void burstToken(String token, boolean stopAtNonOption)
{
    for (int i = 1; i < token.length(); i++)
    {
        String ch = String.valueOf(token.charAt(i));

        if (options.hasOption(ch))
        {
            tokens.add("-" + ch);
            currentOption = options.getOption(ch);

            if (currentOption.hasArg())
            {
                if (token.length() != (i + 1))
                {
                    // pass the remainder of the token as the argument to the option
                    process(token.substring(i + 1));
                }
                break;
            }
        }
        else if (stopAtNonOption)
        {
            // if we should stop at non-option tokens, process remainder as an argument or operand
            process(token.substring(i));
            break;
        }
        else
        {
            tokens.add(token);
            break;
        }
    }
}
