public void removeIgnoreCase(String key) {
    Validate.notEmpty(key);
    if (attributes == null)
        return;
    Iterator<String> it = new ArrayList<>(attributes.keySet()).iterator();
    while (it.hasNext()) {
        String attrKey = it.next();
        if (attrKey.equalsIgnoreCase(key))
            attributes.remove(attrKey);
    }
}
