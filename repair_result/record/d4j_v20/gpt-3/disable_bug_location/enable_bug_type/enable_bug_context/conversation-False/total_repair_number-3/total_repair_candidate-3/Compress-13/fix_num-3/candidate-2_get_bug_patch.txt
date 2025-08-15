protected void setName(String name) {
    if (name != null) {
        // Replace backslashes with forward slashes to fix WinZIP backslash issue
        this.name = name.replace('\\', '/');
    } else {
        this.name = null;
    }
}
