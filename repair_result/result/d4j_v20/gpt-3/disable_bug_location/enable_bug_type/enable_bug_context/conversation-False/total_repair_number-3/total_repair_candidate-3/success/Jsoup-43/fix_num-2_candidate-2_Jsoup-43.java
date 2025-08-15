private static <E extends Element> Integer indexInList(Element search, List<E> elements) {
    Validate.notNull(search);
    Validate.notNull(elements);

    for (int i = 0; i < elements.size(); i++) {
        E element = elements.get(i);
        // Use == instead of equals, to ensure the exact instance is found
        if (element == search)
            return i;
    }
    return null;
}
