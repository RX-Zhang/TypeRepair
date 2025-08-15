public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    String name = getName();
    // Defensive check for null and normalize trailing slash
    return name != null && (name.endsWith("/"));
}
