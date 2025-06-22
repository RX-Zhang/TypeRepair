public boolean isDirectory() {
    try {
        if (file != null) {
            return file.isDirectory();
        }
    } catch (SecurityException e) {
        // Unable to access file attribute, fall back to other checks
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
