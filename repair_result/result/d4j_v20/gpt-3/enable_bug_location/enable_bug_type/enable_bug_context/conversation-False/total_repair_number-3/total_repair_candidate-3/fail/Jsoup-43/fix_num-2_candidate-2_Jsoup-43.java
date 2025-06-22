private static <E extends Element> Integer indexInList(Element search, List<E> elements) {
    Validate.notNull(search);
    Validate.notNull(elements);

    for (int i = 0; i < elements.size(); i++) {
        E element = elements.get(i);
        if (element.equals(search)) {
            return i; // Return the index of the matching element
        }
    }
    return -1; // Return -1 instead of null if the element is not found
}
