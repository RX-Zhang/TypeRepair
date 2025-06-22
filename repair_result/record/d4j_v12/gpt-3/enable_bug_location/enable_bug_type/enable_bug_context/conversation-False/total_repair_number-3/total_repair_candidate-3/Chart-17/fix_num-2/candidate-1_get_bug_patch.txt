public Object clone() throws CloneNotSupportedException {
    int itemCount = getItemCount();
    if (itemCount == 0) {
        // Create a shallow copy when there are no items
        TimeSeries clone = (TimeSeries) super.clone();
        clone.data = new java.util.ArrayList();
        return clone;
    } else {
        Object clone = createCopy(0, itemCount - 1);
        return clone;
    }
}
