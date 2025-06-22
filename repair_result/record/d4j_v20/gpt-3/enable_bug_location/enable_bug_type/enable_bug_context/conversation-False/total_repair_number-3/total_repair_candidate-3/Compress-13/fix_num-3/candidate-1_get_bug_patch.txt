protected void setName(String name) {
    // Normalize the name to use forward slashes instead of backslashes
    this.name = name.replace("\\", "/");
}
