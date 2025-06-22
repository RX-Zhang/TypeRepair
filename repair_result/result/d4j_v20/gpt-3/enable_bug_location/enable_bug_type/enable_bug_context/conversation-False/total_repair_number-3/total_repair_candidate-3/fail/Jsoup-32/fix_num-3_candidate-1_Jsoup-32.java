public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames(); // This line does not actually copy the classNames set
    clone.classNames = new LinkedHashSet<>(this.classNames); // Correctly clone the classNames set
    return clone;
}
