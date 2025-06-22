public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    if (!tag.equals(element.tag)) return false;
    if (!classNames().equals(element.classNames())) return false;
    if (!id().equals(element.id())) return false;
    if (!ownText().equals(element.ownText())) return false;

    return true;
}
