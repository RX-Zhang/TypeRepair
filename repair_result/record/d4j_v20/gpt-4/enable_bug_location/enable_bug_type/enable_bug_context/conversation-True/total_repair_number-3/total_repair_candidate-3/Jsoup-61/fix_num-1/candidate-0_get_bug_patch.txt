public boolean hasClass(String className) {
    // Use classNames() method to get set of classes and check ignoring case
    Set<String> classes = classNames();
    for (String c : classes) {
        if (c.equalsIgnoreCase(className)) {
            return true;
        }
    }
    return false;
}
