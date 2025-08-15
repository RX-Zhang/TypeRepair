public boolean isDirectory() {
    try {
        if (file != null) {
            if (file.isDirectory()) {
                return true;
            }
        }

        if (linkFlag == LF_DIR) {
            return true;
        }

        String name = getName();
        if (name != null && name.endsWith("/")) {
            return true;
        }
    } catch (IOException e) {
        // Handle or log exception if getName() or other file operations throw IOException
        // In this context, return false as fallback
    }

    return false;
}
