public void setSelected(Option option) throws AlreadySelectedException
{
    if (option == null)
    {
        // reset the option previously selected
        selected = null;
        return;
    }
    
    // Check if the selected option is null or if the new option is the same as the previously selected option
    if (selected == null || selected.equals(option.getLongOpt()))
    {
        selected = option.getLongOpt();
    }
    else
    {
        throw new AlreadySelectedException(this, option);
    }
}
