protected void setName(String name) {
    // Replace backslashes with forward slashes to normalize the entry name
    this.name = name.replace('\\', '/');
}
