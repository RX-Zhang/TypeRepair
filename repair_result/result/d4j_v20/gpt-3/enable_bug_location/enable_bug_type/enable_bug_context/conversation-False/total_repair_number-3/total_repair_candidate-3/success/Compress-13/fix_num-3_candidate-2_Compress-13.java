protected void setName(String name) {
    // Normalize the name to use a forward slash instead of a backslash
    this.name = name.replace("\\", "/");
}
