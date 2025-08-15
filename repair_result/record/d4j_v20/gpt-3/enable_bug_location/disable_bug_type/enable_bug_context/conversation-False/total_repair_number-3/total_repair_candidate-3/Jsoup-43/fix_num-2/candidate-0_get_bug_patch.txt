private static <E extends Element> Integer indexInList(Element search, List<E> elements) {
    Validate.notNull(search);
    Validate.notNull(elements);

    for (int i = 0; i < elements.size(); i++) {
        E element = elements.get(i);
        // Use reference equality instead of equals to avoid confusion with duplicate content elements
        if (element == search)
            return i;
    }
    return null;
}
