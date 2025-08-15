protected void setName(String name) {
    // Replace backslashes with forward slashes to normalize entry names
    if (name != null) {
        this.name = name.replace('\\', '/');
    } else {
        this.name = null;
    }
}
