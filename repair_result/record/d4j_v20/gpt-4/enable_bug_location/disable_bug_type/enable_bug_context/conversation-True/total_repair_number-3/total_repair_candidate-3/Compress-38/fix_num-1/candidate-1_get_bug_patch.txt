public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    // Adding boundary check for linkFlag to prevent potential RuntimeError
    if (linkFlag == LF_DIR) {
        return true;
    }

    // Adding boundary check for name to prevent potential RuntimeError
    String entryName = getName();
    if (entryName != null && entryName.endsWith("/")) {
        return true;
    }

    return false;
}
