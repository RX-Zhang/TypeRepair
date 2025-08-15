private static <E extends Element> Integer indexInList(Element search, List<E> elements) {
    Validate.notNull(search);
    Validate.notNull(elements);

    for (int i = 0; i < elements.size(); i++) {
        E element = elements.get(i);
        // Use identity comparison instead of equals to find the exact instance
        if (element == search)
            return i;
    }
    return null;
}
