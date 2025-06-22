public Object clone() throws CloneNotSupportedException {
    int itemCount = getItemCount();
    if (itemCount == 0) {
        // Return a shallow copy when no items present
        return super.clone();
    }
    Object clone = createCopy(0, itemCount - 1);
    return clone;
}
