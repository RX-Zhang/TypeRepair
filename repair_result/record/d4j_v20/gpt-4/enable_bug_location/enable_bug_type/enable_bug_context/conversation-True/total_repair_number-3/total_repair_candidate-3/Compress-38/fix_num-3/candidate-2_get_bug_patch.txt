public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    String name = normalizeFileName(getName(), preserveLeadingSlashes);
    if (name.endsWith("/")) {
        return true;
    }

    return false;
}
