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
    } catch (Exception e) {
        // In case of any exception, default to false to avoid runtime errors
        return false;
    }

    return false;
}
