public Object clone() throws CloneNotSupportedException {
    int itemCount = getItemCount();
    if (itemCount == 0) {
        // avoid IllegalArgumentException in createCopy when no items
        return createCopy(0, 0);
    } else {
        return createCopy(0, itemCount - 1);
    }
}
