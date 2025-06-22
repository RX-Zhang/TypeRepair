protected void setName(String name) {
    if (name != null) {
        this.name = name.replace("\\", "/"); // Convert backslashes to forward slashes
    } else {
        this.name = null;
    }
}
