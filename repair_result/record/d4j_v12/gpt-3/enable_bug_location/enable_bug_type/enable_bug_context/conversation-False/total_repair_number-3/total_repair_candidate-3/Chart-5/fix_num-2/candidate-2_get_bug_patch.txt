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
            // index coming from binarySearch will be negative if not found,
            // so insert at the correct sorted position
            int insertionIndex = (index < 0) ? (-index - 1) : this.data.size();
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
