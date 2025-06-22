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
        
        // reset the OptionBuilder properties
        OptionBuilder.reset();

        // Ensure that the description is reset after creating the option
        if (description != null) {
            throw new IllegalArgumentException("Description should be reset after creating the option");
        }

    // return the Option instance
    return option;
}
