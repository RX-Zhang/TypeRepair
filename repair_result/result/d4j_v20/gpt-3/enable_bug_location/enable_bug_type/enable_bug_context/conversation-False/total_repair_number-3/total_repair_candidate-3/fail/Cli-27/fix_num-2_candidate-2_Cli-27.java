public void setSelected(Option option) throws AlreadySelectedException
{
    if (option == null)
    {
        // reset the option previously selected
        selected = null;
        return;
    }
    
    // Update the logic to ensure that the option is added
    // to the selected variable when it is being set for the first time
    if (selected == null) 
    {
        selected = option.getOpt();
    }
    else if (!selected.equals(option.getOpt())) 
    {
        throw new AlreadySelectedException(this, option);
    }
}
