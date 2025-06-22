public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    return this.tag().equals(element.tag())
            && this.id().equals(element.id())
            && this.className().equals(element.className())
            && this.text().equals(element.text());
}
