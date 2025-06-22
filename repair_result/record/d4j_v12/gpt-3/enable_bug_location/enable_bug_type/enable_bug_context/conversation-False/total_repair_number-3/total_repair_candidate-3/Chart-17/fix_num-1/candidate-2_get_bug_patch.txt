public Object clone() throws CloneNotSupportedException {
    int itemCount = getItemCount();
    if (itemCount == 0) {
        // No items to copy, create an empty copy safely
        return createCopy(0, 0);
    }
    Object clone = createCopy(0, itemCount - 1);
    return clone;
}
