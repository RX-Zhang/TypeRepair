public XYDataItem addOrUpdate(Number x, Number y) {
    if (x == null) {
        throw new IllegalArgumentException("Null 'x' argument.");
    }

    XYDataItem overwritten = null;
    int index = indexOf(x);
    if (index >= 0 && !this.allowDuplicateXValues) {
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
        if (this.autoSort) {
            // When autoSort is true, indexOf uses Collections.binarySearch.
            // binarySearch returns (-(insertion point) - 1) if not found,
            // so insertion point = -index - 1.
            int insertionPoint = (index < 0) ? (-index - 1) : this.data.size();
            this.data.add(insertionPoint, new XYDataItem(x, y));
        }
        else {
            this.data.add(new XYDataItem(x, y));
        }
        // check if this addition will exceed the maximum item count...
        if (getItemCount() > this.maximumItemCount) {
            this.data.remove(0);
        }
    }
    fireSeriesChanged();
    return overwritten;
}
