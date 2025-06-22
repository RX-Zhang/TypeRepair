public boolean hasClass(String className) {
    Set<String> classNames = classNames();
    for (String cls : classNames) {
        if (cls.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
