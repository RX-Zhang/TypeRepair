public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames = new LinkedHashSet<>(this.classNames); // Ensures cloned classNames are separate
    return clone;
}
