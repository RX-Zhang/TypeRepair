public boolean isDirectory() {
    if (file != null && file.exists()) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    String name = getName();
    if (name != null && name.endsWith("/")) {
        return true;
    }

    return false;
}
