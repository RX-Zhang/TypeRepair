public Object clone() throws CloneNotSupportedException {
    if (getItemCount() == 0) {
        // createCopy requires start <= end, so avoid calling it when no items exist
        TimeSeries copy = (TimeSeries) super.clone();
        copy.data = new java.util.ArrayList();
        return copy;
    }
    Object clone = createCopy(0, getItemCount() - 1);
    return clone;
}
