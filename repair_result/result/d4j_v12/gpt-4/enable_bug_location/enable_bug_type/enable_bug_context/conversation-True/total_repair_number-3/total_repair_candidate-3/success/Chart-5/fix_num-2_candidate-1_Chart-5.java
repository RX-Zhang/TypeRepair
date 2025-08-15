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
            if (index < 0) {
                this.data.add(-index - 1, new XYDataItem(x, y));
            } else {
                // If duplicates allowed and autoSort true, and same x value found, append at the end.
                this.data.add(new XYDataItem(x, y));
            }
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
