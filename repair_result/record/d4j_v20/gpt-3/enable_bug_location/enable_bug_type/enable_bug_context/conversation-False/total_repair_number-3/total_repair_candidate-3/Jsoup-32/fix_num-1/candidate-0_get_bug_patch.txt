public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames(); // This line does not actually clone the classNames Set
    clone.classNames = new LinkedHashSet<>(this.classNames); // Correctly clone the classNames Set
    return clone;
}
