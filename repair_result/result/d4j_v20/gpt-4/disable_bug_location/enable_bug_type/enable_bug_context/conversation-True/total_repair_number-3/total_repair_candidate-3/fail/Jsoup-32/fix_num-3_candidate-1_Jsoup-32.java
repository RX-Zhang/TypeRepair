public Element clone() {
    Element clone = (Element) super.clone();
    clone.classNames(classNames == null ? null : new LinkedHashSet<>(classNames));
    return clone;
}
