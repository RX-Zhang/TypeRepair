public void removeIgnoreCase(String key) {
    Validate.notEmpty(key);
    if (attributes == null)
        return;
    synchronized (attributes) {
        Iterator<String> it = attributes.keySet().iterator();
        while (it.hasNext()) {
            String attrKey = it.next();
            if (attrKey.equalsIgnoreCase(key)) {
                it.remove();
            }
        }
    }
}
