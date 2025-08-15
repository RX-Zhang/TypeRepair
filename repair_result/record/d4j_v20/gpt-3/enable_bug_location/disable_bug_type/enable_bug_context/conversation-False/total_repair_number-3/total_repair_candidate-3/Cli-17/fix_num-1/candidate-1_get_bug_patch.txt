protected void burstToken(String token, boolean stopAtNonOption)
{
    for (int i = 1; i < token.length(); i++)
    {
        String ch = String.valueOf(token.charAt(i));

        if (options.hasOption(ch))
        {
            tokens.add("-" + ch);
            currentOption = options.getOption(ch);

            if (currentOption.hasArg() && (token.length() != (i + 1)))
            {
                tokens.add(token.substring(i + 1));
                break;
            }
            else if (currentOption.hasArg())
            {
                // No more chars left; option expects an argument, but none found here
                // so just break to process the next token later.
                break;
            }
        }
        else if (stopAtNonOption)
        {
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
