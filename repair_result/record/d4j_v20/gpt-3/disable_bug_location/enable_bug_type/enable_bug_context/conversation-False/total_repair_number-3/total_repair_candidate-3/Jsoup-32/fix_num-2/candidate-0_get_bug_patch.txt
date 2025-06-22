public Element clone() {
    Element clone = (Element) super.clone();
    if (this.classNames != null) {
        // Create a new LinkedHashSet to avoid shared reference
        clone.classNames = new LinkedHashSet<>(this.classNames);
    }
    return clone;
}
