public boolean hasClass(String className) {
    final String classAttr = attributes.get("class");
    if (classAttr == null || classAttr.isEmpty()) {
        return false;
    }

    Set<String> classNames = classNames();
    for (String clsName : classNames) {
        if (clsName.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
