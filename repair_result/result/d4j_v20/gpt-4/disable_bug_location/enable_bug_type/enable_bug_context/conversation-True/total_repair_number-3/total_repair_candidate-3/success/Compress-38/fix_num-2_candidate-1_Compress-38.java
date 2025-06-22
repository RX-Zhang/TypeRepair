public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (isPaxHeader()) {
        return false; // Prevent error when parsing PAX headers
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    String name = getName();
    if (name == null) {
        return false;
    }
    
    if (name.endsWith("/")) {
        return true;
    }

    return false;
}
