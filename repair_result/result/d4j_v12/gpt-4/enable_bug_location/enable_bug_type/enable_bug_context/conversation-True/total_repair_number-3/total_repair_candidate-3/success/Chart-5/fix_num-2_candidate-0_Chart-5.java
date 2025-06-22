public XYDataItem addOrUpdate(Number x, Number y) {
    if (x == null) {
        throw new IllegalArgumentException("Null 'x' argument.");
    }

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
        if (this.autoSort) {
            // For a sorted series, indexOf returns (-(insertion_point) - 1) if not found.
            // So if index is negative, calculate insertion point.
            int insertionIndex = (index < 0) ? (-index - 1) : this.data.size();
            this.data.add(insertionIndex, new XYDataItem(x, y));
        }
        else {
            this.data.add(new XYDataItem(x, y));
        }
        if (getItemCount() > getMaximumItemCount()) {
            this.data.remove(0);
        }
    }
    fireSeriesChanged();
    return overwritten;
}
