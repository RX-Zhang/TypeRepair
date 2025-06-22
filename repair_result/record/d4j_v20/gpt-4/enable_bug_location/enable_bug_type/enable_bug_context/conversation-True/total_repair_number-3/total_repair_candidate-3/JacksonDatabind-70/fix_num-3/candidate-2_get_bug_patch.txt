public void remove(SettableBeanProperty propToRm)
{
    ArrayList<SettableBeanProperty> props = new ArrayList<SettableBeanProperty>(_size);
    String key = getPropertyName(propToRm);
    boolean found = false;

    for (int i = 0, end = _hashArea.length; i < end; i += 2) {
        SettableBeanProperty prop = (SettableBeanProperty) _hashArea[i+1];
        if (prop == null) {
            continue;
        }
        if (!found) {
            // 09-Jan-2017, tatu: Important: must check name slot and NOT property name,
            //   as only former is lower-case in case-insensitive case
            String propName = (String) _hashArea[i];
            found = key.equals(propName);
            if (found) {
                // need to leave a hole here
                _propsInOrder[_findFromOrdered(prop)] = null;
                continue;
            }
        }
        props.add(prop);
    }
    if (!found) {
        throw new NoSuchElementException("No entry '"+propToRm.getName()+"' found, can't remove");
    }
    init(props);
}
