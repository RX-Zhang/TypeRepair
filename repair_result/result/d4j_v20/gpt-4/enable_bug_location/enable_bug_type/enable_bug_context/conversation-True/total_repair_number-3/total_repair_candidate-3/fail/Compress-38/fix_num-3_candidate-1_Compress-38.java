public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    String name = getName();
    // Use normalized name to avoid issues with trailing slashes causing errors
    name = normalizeFileName(name, preserveLeadingSlashes);

    if (name.endsWith("/")) {
        return true;
    }

    return false;
}
