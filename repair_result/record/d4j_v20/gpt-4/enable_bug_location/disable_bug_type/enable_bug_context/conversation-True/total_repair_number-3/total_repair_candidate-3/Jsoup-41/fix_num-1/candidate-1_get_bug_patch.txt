public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    // Compare tag names
    if (!this.tagName().equals(element.tagName())) return false;

    // Compare id attribute
    if (!this.id().equals(element.id())) return false;

    // Compare class attribute
    if (!this.className().equals(element.className())) return false;

    // Compare text content
    if (!this.text().equals(element.text())) return false;

    return true;
}
