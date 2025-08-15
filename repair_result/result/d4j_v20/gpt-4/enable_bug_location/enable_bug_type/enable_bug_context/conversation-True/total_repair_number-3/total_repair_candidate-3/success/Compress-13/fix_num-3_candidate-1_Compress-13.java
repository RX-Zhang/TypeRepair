protected void setName(String name) {
    // Normalize the entry name by replacing backslashes with forward slashes
    if (name != null) {
        this.name = name.replace('\\', '/');
    } else {
        this.name = null;
    }
}
