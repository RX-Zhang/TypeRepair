private void appendOption(final StringBuffer buff, 
                          final Option option, 
                          final boolean required)
{
    if (!required)
    {
        buff.append("[");
    }

    if (option.getOpt() != null)
    {
        buff.append(getOptPrefix()).append(option.getOpt());
    }
    else
    {
        buff.append(getLongOptPrefix()).append(option.getLongOpt());
    }

    // if the Option has a value
    String argName = option.getArgName();
    if (option.hasArg() && (argName != null) && (argName.length() > 0))
    {
        buff.append(" <").append(argName).append(">");
    }

    // if the Option is not a required option
    if (!required)
    {
        buff.append("]");
    }
}
