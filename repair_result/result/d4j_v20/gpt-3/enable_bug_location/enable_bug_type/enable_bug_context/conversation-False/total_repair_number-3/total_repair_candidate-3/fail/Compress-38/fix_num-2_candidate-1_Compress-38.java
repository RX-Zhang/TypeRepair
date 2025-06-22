public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    String entryName = getName();
    if (entryName != null && entryName.endsWith("/")) {
        return true;
    }

    return false;
}
