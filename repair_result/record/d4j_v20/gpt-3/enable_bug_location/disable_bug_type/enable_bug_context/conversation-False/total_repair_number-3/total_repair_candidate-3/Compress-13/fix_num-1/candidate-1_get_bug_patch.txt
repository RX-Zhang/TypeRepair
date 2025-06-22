protected void setName(String name) {
    if (name != null && platform == PLATFORM_FAT) {
        // Replace backslashes with forward slashes for entries from FAT platform (Windows)
        this.name = name.replace('\\', '/');
    } else {
        this.name = name;
    }
}
