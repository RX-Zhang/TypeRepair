public Object clone() throws CloneNotSupportedException {
    int itemCount = getItemCount();
    if (itemCount == 0) {
        // Create an empty copy if no items exist
        return createCopy(0, 0);
    }
    Object clone = createCopy(0, itemCount - 1);
    return clone;
}
