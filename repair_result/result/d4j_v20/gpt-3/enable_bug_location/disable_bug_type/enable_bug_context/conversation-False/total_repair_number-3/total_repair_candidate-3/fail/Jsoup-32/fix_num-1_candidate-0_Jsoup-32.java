public Element clone() {
    Element clone = (Element) super.clone();
    // Deep copy the classNames set so the clone has its own copy
    if (this.classNames != null) {
        clone.classNames = new LinkedHashSet<>(this.classNames);
    }
    return clone;
}
