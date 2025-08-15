public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    // Compare tag name and text content for equality
    if (!tagName().equals(element.tagName())) return false;
    return text().equals(element.text());
}
