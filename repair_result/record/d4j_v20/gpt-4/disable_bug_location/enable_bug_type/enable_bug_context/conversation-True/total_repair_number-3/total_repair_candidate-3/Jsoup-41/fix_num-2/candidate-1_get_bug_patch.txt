public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    if (tag != null ? !tag.equals(element.tag) : element.tag != null) return false;

    // For equality, you may also want to check other relevant fields if any
    // but based on provided info, tag is the main distinguishing feature.

    return true;
}
