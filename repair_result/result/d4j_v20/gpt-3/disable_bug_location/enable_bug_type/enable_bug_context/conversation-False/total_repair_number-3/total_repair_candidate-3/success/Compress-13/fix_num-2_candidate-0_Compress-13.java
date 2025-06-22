protected void setName(String name) {
    if (name != null) {
        // WinZip on Windows uses backslashes as path separators in zip entries,
        // but the standard expects forward slashes.
        this.name = name.replace('\\', '/');
    } else {
        this.name = null;
    }
}
