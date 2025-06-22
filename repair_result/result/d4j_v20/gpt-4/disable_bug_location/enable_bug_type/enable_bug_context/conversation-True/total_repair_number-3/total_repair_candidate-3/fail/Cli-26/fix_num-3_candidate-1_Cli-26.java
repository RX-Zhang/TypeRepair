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

    // reset the OptionBuilder properties BEFORE creating the Option instance to avoid state leakage
    OptionBuilder.reset();

    // return the Option instance
    return option;
}
