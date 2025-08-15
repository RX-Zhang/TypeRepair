private static <E extends Element> Integer indexInList(Element search, List<E> elements) {
    Validate.notNull(search);
    Validate.notNull(elements);

    for (int i = 0; i < elements.size(); i++) {
        E element = elements.get(i);
        // Use reference equality to find the exact Element instance in the list,
        // since multiple elements can have equal content but are distinct instances.
        if (element == search)
            return i;
    }
    return null;
}
