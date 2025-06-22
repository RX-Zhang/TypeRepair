public boolean hasClass(String className) {
    final Set<String> classNames = classNames();
    for (String classNameInSet : classNames) {
        if (classNameInSet.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
