public boolean hasClass(String className) {
    final String classAttr = attributes.get("class");
    if (classAttr == null || classAttr.isEmpty()) {
        return false;
    }

    final Set<String> classes = classNames();
    for (String cls : classes) {
        if (cls.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
