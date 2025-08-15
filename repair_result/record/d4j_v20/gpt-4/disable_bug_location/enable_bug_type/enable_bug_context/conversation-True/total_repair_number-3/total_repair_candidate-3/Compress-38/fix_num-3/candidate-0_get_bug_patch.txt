public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    try {
        String name = getName();
        if (name != null && name.endsWith("/")) {
            return true;
        }
    } catch (Exception e) {
        // Defensive catch to prevent runtime errors from malformed names
        return false;
    }

    return false;
}
