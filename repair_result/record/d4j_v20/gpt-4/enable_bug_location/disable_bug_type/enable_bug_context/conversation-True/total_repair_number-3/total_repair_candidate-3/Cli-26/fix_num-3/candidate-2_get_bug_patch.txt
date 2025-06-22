public static Option create(String opt) throws IllegalArgumentException
{
    // create the option
    Option option = new Option(opt, description);

    // set the option properties
    option.setLongOpt(longopt);
    option.setRequired(required);
    option.setOptionalArg(optionalArg);
    option.setArgs(numberOfArgs);
    option.setType(type);
    option.setValueSeparator(valuesep);
    option.setArgName(argName);

    // return the Option instance
    // reset the OptionBuilder properties AFTER the Option is created and configured
    OptionBuilder.reset();

    return option;
}
