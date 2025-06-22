protected void setName(String name) {
    if (name != null) {
        // Normalize backslashes to forward slashes to handle WinZip backslash workaround
        this.name = name.replace('\\', '/');
    } else {
        this.name = null;
    }
}
