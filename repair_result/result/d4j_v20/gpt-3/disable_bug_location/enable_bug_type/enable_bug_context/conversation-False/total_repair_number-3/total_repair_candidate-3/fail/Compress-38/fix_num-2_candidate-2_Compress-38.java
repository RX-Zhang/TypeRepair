public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    // Ensure the name is not null before checking if it ends with '/'
    String name = getName();
    if (name != null && name.endsWith("/")) {
        return true;
    }

    return false;
}
