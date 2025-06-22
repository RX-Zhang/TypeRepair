public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames(); // This line should not be called to initialize classNames
    clone.classNames = new LinkedHashSet<>(this.classNames); // Properly copy the classNames set
    return clone;
}
