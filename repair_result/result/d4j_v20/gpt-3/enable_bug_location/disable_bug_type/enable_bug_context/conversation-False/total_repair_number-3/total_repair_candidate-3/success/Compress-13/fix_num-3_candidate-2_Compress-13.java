protected void setName(String name) {
    if (name != null && name.indexOf('\\') >= 0) {
        name = name.replace('\\', '/');
    }
    this.name = name;
}
