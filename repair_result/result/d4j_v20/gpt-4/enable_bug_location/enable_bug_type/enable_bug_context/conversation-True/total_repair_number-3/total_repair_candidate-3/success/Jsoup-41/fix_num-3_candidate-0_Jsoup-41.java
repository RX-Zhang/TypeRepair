public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    // Compare tag and text content for equality
    if (tag != null ? !tag.equals(element.tag) : element.tag != null) return false;
    String thisText = this.text();
    String otherText = element.text();
    return thisText.equals(otherText);
}
