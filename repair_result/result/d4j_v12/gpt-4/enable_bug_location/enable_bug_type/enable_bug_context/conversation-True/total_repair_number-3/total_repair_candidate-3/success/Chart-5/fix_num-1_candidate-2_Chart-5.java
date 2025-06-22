public XYDataItem addOrUpdate(Number x, Number y) {
    if (x == null) {
        throw new IllegalArgumentException("Null 'x' argument.");
    }

    // if duplicate X values are not permitted, and the series is sorted,
    // indexOf() returns a negative insertion index when not found,
    // and a non-negative index when found
    XYDataItem overwritten = null;
    int index = indexOf(x);
    if (index >= 0 && !getAllowDuplicateXValues()) {
        XYDataItem existing = (XYDataItem) this.data.get(index);
        try {
            overwritten = (XYDataItem) existing.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new SeriesException("Couldn't clone XYDataItem!");
        }
        existing.setY(y);
    }
    else {
        if (getAutoSort()) {
            // index is negative if item not found; calculate insertion point
            int insertionIndex = (index < 0) ? (-index - 1) : getItemCount();
            this.data.add(insertionIndex, new XYDataItem(x, y));
        }
        else {
            this.data.add(new XYDataItem(x, y));
        }
        if (getItemCount() > this.maximumItemCount) {
            this.data.remove(0);
        }
    }
    fireSeriesChanged();
    return overwritten;
}
