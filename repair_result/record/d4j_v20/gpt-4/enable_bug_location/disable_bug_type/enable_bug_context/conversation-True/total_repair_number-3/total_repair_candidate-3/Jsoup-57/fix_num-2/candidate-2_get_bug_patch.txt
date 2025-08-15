public void removeIgnoreCase(String key) {
    Validate.notEmpty(key);
    if (attributes == null)
        return;
    Iterator<Attribute> it = iterator();
    while (it.hasNext()) {
        Attribute attr = it.next();
        if (attr.getKey().equalsIgnoreCase(key)) {
            it.remove();
        }
    }
}
