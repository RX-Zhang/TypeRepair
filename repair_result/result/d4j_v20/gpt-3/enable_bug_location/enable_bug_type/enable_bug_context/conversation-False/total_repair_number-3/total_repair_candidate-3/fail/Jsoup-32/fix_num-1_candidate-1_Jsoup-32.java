public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames(); // This line should not modify the classNames of the clone
    clone.classNames = new LinkedHashSet<>(this.classNames); // Correctly clone the classNames
    return clone;
}
