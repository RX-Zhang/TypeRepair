public boolean hasClass(String className) {
    if (className == null || className.isEmpty()) {
        return false;
    }
    Set<String> classes = classNames();
    for (String classItem : classes) {
        if (classItem.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
