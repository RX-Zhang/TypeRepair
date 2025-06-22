protected void setName(String name) {
    // Replace backslashes with forward slashes to handle WinZip backslash workaround
    this.name = name.replace('\\', '/');
}
