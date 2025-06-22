public boolean hasClass(String className) {
    String classAttr = attributes.get("class");
    if (classAttr == null || classAttr.isEmpty()) {
        return false;
    }

    String[] classes = classAttr.split("\\s+");
    for (String cls : classes) {
        if (cls.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
