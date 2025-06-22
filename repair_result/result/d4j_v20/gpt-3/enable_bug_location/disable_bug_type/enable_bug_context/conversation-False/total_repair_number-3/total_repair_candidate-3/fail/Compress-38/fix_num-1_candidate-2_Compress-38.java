public boolean isDirectory() {
    if (file != null) {
        return file.isDirectory();
    }

    if (linkFlag == LF_DIR) {
        return true;
    }

    // Consider entries with names ending in "/" as directories,
    // but ignore names that are just "/" to avoid treating root as directory.
    String name = getName();
    if (name != null && name.length() > 1 && name.endsWith("/")) {
        return true;
    }

    return false;
}
