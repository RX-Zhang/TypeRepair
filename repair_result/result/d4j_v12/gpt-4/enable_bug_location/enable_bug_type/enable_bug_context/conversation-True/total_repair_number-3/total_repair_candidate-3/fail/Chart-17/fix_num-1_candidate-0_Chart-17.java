public Object clone() throws CloneNotSupportedException {
    if (getItemCount() == 0) {
        // no data, just return a shallow clone
        return super.clone();
    }
    Object clone = createCopy(0, getItemCount() - 1);
    return clone;
}
