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
            // Important: check the key slot, which is lower-cased if caseInsensitive is true
            String currentKey = (String) _hashArea[i];
            found = key.equals(currentKey);
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
