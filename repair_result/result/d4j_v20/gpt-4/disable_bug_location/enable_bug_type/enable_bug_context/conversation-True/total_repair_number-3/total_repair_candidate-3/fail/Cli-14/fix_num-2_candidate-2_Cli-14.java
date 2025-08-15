public void validate(final WriteableCommandLine commandLine)
    throws OptionException {
    // number of options found
    int present = 0;

    // reference to first unexpected option
    Option unexpected = null;

    // Use getters for options, maximum, and minimum
    List optionsList = getOptions();
    int maximum = getMaximum();
    int minimum = getMinimum();

    for (final Iterator i = optionsList.iterator(); i.hasNext();) {
        final Option option = (Option) i.next();

        // needs validation?
        boolean validate = option.isRequired() || option instanceof Group;
        if (validate) {
            option.validate(commandLine);
        }

        // if the child option is present then validate it
        if (commandLine.hasOption(option)) {
            if (++present > maximum) {
                unexpected = option;
                break;
            }

            option.validate(commandLine);
        }
    }

    // too many options
    if (unexpected != null) {
        throw new OptionException(this, ResourceConstants.UNEXPECTED_TOKEN,
                                  unexpected.getPreferredName());
    }

    // too few option
    if (present < minimum) {
        throw new OptionException(this, ResourceConstants.MISSING_OPTION);
    }

    // validate each anonymous argument
    List anonymousList = getAnonymous();
    for (final Iterator i = anonymousList.iterator(); i.hasNext();) {
        final Option option = (Option) i.next();
        option.validate(commandLine);
    }
}
