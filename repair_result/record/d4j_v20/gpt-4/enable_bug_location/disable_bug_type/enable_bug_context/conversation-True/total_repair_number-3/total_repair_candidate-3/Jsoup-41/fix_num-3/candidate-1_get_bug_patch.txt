public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Element element = (Element) o;

    // Consider elements equal if their tag names, class names, and text content are equal
    return this.tagName().equals(element.tagName()) &&
           this.className().equals(element.className()) &&
           this.text().equals(element.text());
}
