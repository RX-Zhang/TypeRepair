public boolean isDirectory() {
    try {
        if (file != null) {
            return file.isDirectory();
        }
    } catch (SecurityException e) {
        // If we cannot access the file, fall back to checking the entry attributes
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    String name = getName();
    if (name != null && name.endsWith("/")) {
        return true;
    }

    return false;
}
