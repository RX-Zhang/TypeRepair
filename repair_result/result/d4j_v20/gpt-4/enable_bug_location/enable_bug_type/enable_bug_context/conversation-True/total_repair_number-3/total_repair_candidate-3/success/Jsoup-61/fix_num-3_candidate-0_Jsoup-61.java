public boolean hasClass(String className) {
    Set<String> classes = classNames();
    for (String cls : classes) {
        if (cls.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
