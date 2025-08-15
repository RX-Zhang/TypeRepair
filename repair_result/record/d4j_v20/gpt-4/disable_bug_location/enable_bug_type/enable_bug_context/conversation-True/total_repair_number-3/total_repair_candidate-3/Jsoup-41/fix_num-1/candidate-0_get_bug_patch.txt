public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    if (tag != null ? !tag.equals(element.tag) : element.tag != null) return false;

    // Additional equality checks can be added here, for example comparing attributes,
    // but based on provided info, only tag is compared.

    return true;
}
