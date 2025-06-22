protected void setName(String name) {
    if (name != null) {
        // Normalize Windows backslashes to forward slashes to handle WinZip backslash filenames correctly
        this.name = name.replace('\\', '/');
    } else {
        this.name = null;
    }
}
