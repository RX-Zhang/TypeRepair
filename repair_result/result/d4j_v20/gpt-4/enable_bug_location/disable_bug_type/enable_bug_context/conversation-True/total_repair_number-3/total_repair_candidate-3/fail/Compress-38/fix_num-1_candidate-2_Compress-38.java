public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    } else {
        throw new RuntimeException("File is null, cannot determine if it is a directory.");
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    if (getName() != null && getName().endsWith("/")) {
        return true;
    }

    return false;
}
