public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames(); // This line should not be here as it does not clone the classNames
    clone.classNames = new LinkedHashSet<>(this.classNames); // Correctly clone the classNames
    return clone;
}
