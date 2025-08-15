public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    // Compare the tags for equality
    return tag != null ? tag.equals(element.tag) : element.tag == null;
}
