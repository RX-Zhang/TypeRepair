public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    // Compare the tag names for equality
    if (!tagName().equals(element.tagName())) return false;

    // Compare the class attribute for equality
    if (!className().equals(element.className())) return false;

    // Compare the text content for equality
    if (!text().equals(element.text())) return false;

    // Compare the id attribute for equality
    if (!id().equals(element.id())) return false;

    return true;
}
