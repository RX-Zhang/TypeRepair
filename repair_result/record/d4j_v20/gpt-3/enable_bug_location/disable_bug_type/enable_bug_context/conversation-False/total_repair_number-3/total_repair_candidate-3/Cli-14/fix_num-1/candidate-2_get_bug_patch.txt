public void validate(final WriteableCommandLine commandLine)
    throws OptionException {
    // number of options found
    int present = 0;

    // reference to first unexpected option
    Option unexpected = null;

    for (final Iterator i = options.iterator(); i.hasNext();) {
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

    // too few options
    if (present < minimum) {
        throw new OptionException(this, ResourceConstants.MISSING_OPTION);
    }

    // validate each anonymous argument
    for (final Iterator i = anonymous.iterator(); i.hasNext();) {
        final Option option = (Option) i.next();
        option.validate(commandLine);
    }

    // Fix to ensure options are properly validated and cast
    for (final Iterator i = options.iterator(); i.hasNext();) {
        final Option option = (Option) i.next();
        if (commandLine.hasOption(option)) {
            Object value = commandLine.getValue(option);
            if (value instanceof File) {
                // Convert File to String if necessary
                value = ((File) value).getAbsolutePath();
            }
            // Assume there's a method to handle the validated value
            option.setValue(value); 
        }
    }
}
