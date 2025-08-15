public boolean hasClass(String className) {
    if (className == null || className.length() == 0) {
        return false;
    }
    Set<String> classes = classNames();
    for (String cls : classes) {
        if (cls.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
