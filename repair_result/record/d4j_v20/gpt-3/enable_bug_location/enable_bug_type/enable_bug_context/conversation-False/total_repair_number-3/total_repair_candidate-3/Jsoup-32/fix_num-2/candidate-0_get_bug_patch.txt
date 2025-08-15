public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames = new LinkedHashSet<>(this.classNames); // Correctly clone the classNames set
    return clone;
}
