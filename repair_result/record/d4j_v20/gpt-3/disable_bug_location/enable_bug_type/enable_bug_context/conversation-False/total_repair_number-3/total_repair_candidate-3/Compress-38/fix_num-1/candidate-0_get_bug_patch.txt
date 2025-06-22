public boolean isDirectory() {
    if (file != null) {
        try {
            return file.isDirectory();
        } catch (SecurityException e) {
            // If we cannot access file properties, fallback to checking linkFlag and name
        }
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
