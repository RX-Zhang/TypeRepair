private static <E extends Element> Integer indexInList(Element search, List<E> elements) {
    Validate.notNull(search);
    Validate.notNull(elements);

    for (int i = 0; i < elements.size(); i++) {
        E element = elements.get(i);
        if (element == search)  // Use reference equality to correctly distinguish elements with same content
            return i;
    }
    return null;
}
