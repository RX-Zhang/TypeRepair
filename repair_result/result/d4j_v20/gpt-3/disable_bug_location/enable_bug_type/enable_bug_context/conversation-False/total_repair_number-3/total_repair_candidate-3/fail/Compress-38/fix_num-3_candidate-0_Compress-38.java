public boolean isDirectory() {
    try {
        if (file != null) {
            return file.isDirectory();
        }
    } catch (SecurityException e) {
        // Cannot determine file type due to security manager,
        // fallback to other checks below.
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    final String name = getName();
    if (name != null && name.endsWith("/")) {
        return true;
    }

    return false;
}
