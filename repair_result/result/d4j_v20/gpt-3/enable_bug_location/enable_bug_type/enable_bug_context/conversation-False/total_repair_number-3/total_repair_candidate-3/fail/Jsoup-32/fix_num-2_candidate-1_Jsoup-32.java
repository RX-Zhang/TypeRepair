public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames = new LinkedHashSet<>(this.classNames); // Ensure cloned element has its own copy of classNames
    return clone;
}
