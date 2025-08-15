public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    if (!tagName().equals(element.tagName())) return false;
    if (!className().equals(element.className())) return false;
    if (!text().equals(element.text())) return false;

    return true;
}
