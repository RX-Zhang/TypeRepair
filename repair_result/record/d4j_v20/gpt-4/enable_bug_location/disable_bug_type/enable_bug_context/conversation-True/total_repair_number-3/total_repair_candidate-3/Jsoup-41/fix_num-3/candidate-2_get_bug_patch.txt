public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    // Elements are equal if their tag names, attributes, and text content are equal
    if (!this.tagName().equals(element.tagName())) return false;
    if (!this.className().equals(element.className())) return false;
    if (!this.text().equals(element.text())) return false;

    return true;
}
