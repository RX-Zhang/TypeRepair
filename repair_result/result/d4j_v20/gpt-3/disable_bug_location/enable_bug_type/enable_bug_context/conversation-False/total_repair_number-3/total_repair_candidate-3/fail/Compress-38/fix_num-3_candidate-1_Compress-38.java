public boolean isDirectory() {
    try {
        if (file != null) {
            return file.isDirectory();
        }

        if (linkFlag == LF_DIR) {
            return true;
        }

        if (getName() != null && getName().endsWith("/")) {
            return true;
        }

        return false;
    } catch (Exception e) {
        // Defensive: if any unexpected error occurs, assume it's not a directory
        return false;
    }
}
