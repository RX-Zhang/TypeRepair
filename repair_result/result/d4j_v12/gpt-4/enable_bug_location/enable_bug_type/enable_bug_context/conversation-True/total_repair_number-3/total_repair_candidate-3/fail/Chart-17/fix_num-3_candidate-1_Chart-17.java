public Object clone() throws CloneNotSupportedException {
    if (getItemCount() == 0) {
        return createCopy(0, -1);  // handle empty series case
    }
    Object clone = createCopy(0, getItemCount() - 1);
    return clone;
}
