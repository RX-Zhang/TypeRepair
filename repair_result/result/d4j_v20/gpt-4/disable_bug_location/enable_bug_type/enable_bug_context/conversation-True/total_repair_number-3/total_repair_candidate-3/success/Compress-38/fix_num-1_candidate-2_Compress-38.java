public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    // Skip entries that are Pax headers as they may have names ending with "/"
    if (isPaxHeader()) {
        return false;
    }

    if (getName() != null && getName().endsWith("/")) {
        return true;
    }

    return false;
}
