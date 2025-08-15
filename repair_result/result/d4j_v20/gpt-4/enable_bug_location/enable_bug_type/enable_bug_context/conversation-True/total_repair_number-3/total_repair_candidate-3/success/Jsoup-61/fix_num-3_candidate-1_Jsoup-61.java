public boolean hasClass(String className) {
    final Set<String> classNames = classNames();
    for (String cls : classNames) {
        if (cls.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
