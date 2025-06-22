public Object clone() throws CloneNotSupportedException {
    if (getItemCount() == 0) {
        return createCopy(0, -1);
    } else {
        return createCopy(0, getItemCount() - 1);
    }
}
