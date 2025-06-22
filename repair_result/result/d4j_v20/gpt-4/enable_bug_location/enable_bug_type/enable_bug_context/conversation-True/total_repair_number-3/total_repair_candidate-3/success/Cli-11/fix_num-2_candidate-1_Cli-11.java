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
        buff.append(HelpFormatter.DEFAULT_OPT_PREFIX).append(option.getOpt());
    }
    else
    {
        buff.append(HelpFormatter.DEFAULT_LONG_OPT_PREFIX).append(option.getLongOpt());
    }

    // if the Option has a value and argName is not empty
    if (option.hasArg() && (option.getArgName() != null) && (option.getArgName().length() > 0))
    {
        buff.append(" <").append(option.getArgName()).append(">");
    }

    if (!required)
    {
        buff.append("]");
    }
}
