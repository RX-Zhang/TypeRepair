public Object clone() throws CloneNotSupportedException {
    int itemCount = getItemCount();
    if (itemCount == 0) {
        return createCopy(0, 0); // create an empty copy safely
    } else {
        return createCopy(0, itemCount - 1);
    }
}
