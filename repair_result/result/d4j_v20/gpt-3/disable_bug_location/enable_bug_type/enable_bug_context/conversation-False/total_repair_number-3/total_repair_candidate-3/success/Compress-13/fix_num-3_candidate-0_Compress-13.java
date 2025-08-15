protected void setName(String name) {
    // Fix for WinZip backslash path separator workaround:
    // Replace any backslashes '\' in the name with forward slashes '/'
    if (name != null) {
        this.name = name.replace('\\', '/');
    } else {
        this.name = null;
    }
}
