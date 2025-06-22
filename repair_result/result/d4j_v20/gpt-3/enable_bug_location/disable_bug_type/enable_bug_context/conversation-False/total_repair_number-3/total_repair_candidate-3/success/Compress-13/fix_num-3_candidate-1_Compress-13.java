protected void setName(String name) {
    // Replace backslashes with forward slashes to fix WinZip entry name bug
    this.name = name.replace('\\', '/');
}
