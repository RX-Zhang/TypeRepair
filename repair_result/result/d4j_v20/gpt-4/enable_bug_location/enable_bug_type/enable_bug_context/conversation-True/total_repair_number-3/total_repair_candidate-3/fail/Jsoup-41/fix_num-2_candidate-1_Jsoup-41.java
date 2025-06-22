public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    if (tag != null ? !tag.equals(element.tag) : element.tag != null) return false;

    return true;
}

public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (tag != null ? tag.hashCode() : 0);
    return result;
}
