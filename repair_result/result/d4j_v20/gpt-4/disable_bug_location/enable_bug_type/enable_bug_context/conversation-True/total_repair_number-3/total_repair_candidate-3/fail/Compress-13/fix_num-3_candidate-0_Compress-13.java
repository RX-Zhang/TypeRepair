protected void setName(String name) {
    this.name = name;
    this.rawName = null; // reset rawName to ensure getName() returns the updated name correctly
}
