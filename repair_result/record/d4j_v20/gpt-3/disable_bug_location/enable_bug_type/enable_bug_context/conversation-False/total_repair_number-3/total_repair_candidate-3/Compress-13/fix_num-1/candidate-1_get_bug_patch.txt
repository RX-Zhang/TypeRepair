protected void setName(String name) {
    // Replace backslashes with forward slashes to handle WinZip path issue
    this.name = name.replace('\\', '/');
}
