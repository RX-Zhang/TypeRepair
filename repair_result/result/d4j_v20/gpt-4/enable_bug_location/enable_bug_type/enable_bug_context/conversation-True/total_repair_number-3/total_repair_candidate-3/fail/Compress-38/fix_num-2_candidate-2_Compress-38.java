public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    String name = getName();
    if (name.length() == 0) {
        return false;
    }

    if (name.endsWith("/")) {
        return true;
    }

    return false;
}
