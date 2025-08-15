private static void appendOption(final StringBuffer buff, 
                                 final Option option, 
                                 final boolean required)
{
    if (!required)
    {
        buff.append("[");
    }

    if (option.getOpt() != null)
    {
        buff.append("-").append(option.getOpt());
    }
    else
    {
        buff.append("--").append(option.getLongOpt());
    }

    // if the Option has a value and a non-empty argument name
    String argName = option.getArgName();
    if (option.hasArg() && argName != null && !argName.isEmpty())
    {
        buff.append(" <").append(argName).append(">");
    }

    if (!required)
    {
        buff.append("]");
    }
}
