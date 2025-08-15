public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    // Compare the classNames to match elements with the same classes
    return this.classNames().equals(element.classNames()) && this.text().equals(element.text());
}
