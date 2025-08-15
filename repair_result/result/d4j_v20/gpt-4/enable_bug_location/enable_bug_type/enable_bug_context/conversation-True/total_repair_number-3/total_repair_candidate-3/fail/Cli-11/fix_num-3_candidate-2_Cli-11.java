private static void appendOption(final StringBuffer buff, 
                                 final Option option, 
                                 final boolean required,
                                 final HelpFormatter formatter)
{
    if (!required)
    {
        buff.append("[");
    }

    if (option.getOpt() != null)
    {
        buff.append(formatter.getOptPrefix()).append(option.getOpt());
    }
    else
    {
        buff.append(formatter.getLongOptPrefix()).append(option.getLongOpt());
    }

    // if the Option has a value and the argName is not empty
    if (option.hasArg() && (option.getArgName() != null) && (option.getArgName().length() > 0))
    {
        buff.append(" <").append(option.getArgName()).append(">");
    }

    // if the Option is not a required option
    if (!required)
    {
        buff.append("]");
    }
}
