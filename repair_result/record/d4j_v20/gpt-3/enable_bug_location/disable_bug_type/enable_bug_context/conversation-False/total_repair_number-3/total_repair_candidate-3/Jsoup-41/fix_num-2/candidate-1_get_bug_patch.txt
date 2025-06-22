public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    // Compare the tag attribute for equality
    if (tag != null ? !tag.equals(element.tag) : element.tag != null) return false;

    return true;
}
