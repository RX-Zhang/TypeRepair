public boolean isDirectory() {
    try {
        if (file != null) {
            return file.isDirectory();
        }

        if (linkFlag == LF_DIR) {
            return true;
        }

        // Treat PAX headers specially to prevent parsing errors
        if (isPaxHeader()) {
            return false;
        }

        if (getName() != null && getName().endsWith("/")) {
            return true;
        }
    } catch (Exception e) {
        // Defensive fallback in case of any error
        return false;
    }

    return false;
}
