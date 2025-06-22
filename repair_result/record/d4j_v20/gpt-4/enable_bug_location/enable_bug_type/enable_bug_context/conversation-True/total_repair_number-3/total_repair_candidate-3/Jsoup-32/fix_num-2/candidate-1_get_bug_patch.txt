public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames = new LinkedHashSet<>(classNames());
    return clone;
}
