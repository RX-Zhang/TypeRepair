protected void setName(String name) {
    if (name != null) {
        this.name = name.replace("\\", "/"); // Replace backslashes with forward slashes
    } else {
        this.name = null;
    }
}
