public Element clone() {
    Element clone = (Element) super.clone();
    if (this.classNames != null) {
        clone.classNames = new LinkedHashSet<>(this.classNames);
    }
    return clone;
}
