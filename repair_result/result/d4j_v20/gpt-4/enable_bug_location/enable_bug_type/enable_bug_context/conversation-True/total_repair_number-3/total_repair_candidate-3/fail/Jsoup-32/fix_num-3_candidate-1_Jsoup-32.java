public Element clone() {
    Element clone = (Element) super.clone();
    if (classNames != null) {
        clone.classNames(new LinkedHashSet<>(classNames));
    }
    return clone;
}
