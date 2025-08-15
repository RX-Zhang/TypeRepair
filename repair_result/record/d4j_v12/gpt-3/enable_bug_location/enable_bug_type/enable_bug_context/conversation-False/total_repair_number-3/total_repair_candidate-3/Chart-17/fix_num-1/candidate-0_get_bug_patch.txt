public Object clone() throws CloneNotSupportedException {
    int itemCount = getItemCount();
    if (itemCount == 0) {
        // Return a shallow clone with empty data to avoid IllegalArgumentException in createCopy
        TimeSeries copy = (TimeSeries) super.clone();
        copy.data = new java.util.ArrayList();
        return copy;
    }
    Object clone = createCopy(0, itemCount - 1);
    return clone;
}
