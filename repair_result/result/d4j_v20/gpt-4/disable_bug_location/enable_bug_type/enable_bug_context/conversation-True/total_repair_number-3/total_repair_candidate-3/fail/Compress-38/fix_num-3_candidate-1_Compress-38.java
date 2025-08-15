public boolean isDirectory() {
    try {
        if (file != null) {
            return file.isDirectory();
        }

        if (linkFlag == LF_DIR) {
            return true;
        }

        String name = getName();
        if (name != null && name.endsWith("/")) {
            return true;
        }
    } catch (Exception e) {
        // Log the exception if a logging framework is available,
        // or handle it appropriately.
        // Returning false as a safe fallback
        return false;
    }

    return false;
}
