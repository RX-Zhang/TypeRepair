private static <E extends Element> Integer indexInList(Element search, List<E> elements) {
    Validate.notNull(search);
    Validate.notNull(elements);

    for (int i = 0; i < elements.size(); i++) {
        E element = elements.get(i);
        if (element == search) // Change equals to reference comparison
            return i;
    }
    return -1; // Return -1 instead of null for not found case
}
