public Object clone() throws CloneNotSupportedException {
    int itemCount = getItemCount();
    if (itemCount == 0) {
        // If empty, create an empty copy without calling createCopy with invalid indices
        TimeSeries copy = (TimeSeries) super.clone();
        copy.data = new java.util.ArrayList();
        return copy;
    }
    Object clone = createCopy(0, itemCount - 1);
    return clone;
}
