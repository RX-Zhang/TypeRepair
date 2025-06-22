public void removeIgnoreCase(String key) {
    Validate.notEmpty(key);
    if (attributes == null)
        return;
    Iterator<Attribute> it = iterator();
    while (it.hasNext()) {
        Attribute attribute = it.next();
        if (attribute.getKey().equalsIgnoreCase(key)) {
            it.remove();
        }
    }
}
