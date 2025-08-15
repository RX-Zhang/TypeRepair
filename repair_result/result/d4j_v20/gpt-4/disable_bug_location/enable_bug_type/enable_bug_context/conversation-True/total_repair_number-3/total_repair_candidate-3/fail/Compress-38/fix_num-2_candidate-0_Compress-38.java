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
    } catch (IOException | SecurityException e) {
        // Handle or log the exception as needed
        // For safety, return false if an error occurs
        return false;
    }

    return false;
}
