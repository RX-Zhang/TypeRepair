public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    // Normalize the name before checking for trailing slash
    String normalized = normalizeFileName(getName(), preserveLeadingSlashes);
    if (normalized.endsWith("/")) {
        return true;
    }

    return false;
}
