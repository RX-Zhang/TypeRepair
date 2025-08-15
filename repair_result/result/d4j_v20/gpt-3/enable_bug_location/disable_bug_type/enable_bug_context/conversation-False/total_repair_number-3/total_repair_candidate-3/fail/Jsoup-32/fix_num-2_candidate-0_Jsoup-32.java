public Element clone() {
    Element clone = (Element) super.clone();
    if (clone.classNames != null) {
        clone.classNames = new LinkedHashSet<>(clone.classNames);
    }
    return clone;
}
