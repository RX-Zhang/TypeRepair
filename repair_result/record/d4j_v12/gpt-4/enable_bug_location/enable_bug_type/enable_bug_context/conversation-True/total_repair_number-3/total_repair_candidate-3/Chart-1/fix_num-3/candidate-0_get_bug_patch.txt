public LegendItemCollection getLegendItems() {
    LegendItemCollection result = new LegendItemCollection();
    CategoryPlot plot = getPlot();
    if (plot == null) {
        return result;
    }
    int index = plot.getIndexOf(this);
    CategoryDataset dataset = plot.getDataset(index);
    if (dataset == null) {
        return result;
    }
    int seriesCount = dataset.getRowCount();
    if (plot.getRowRenderingOrder().equals(SortOrder.ASCENDING)) {
        for (int i = 0; i < seriesCount; i++) {
            if (isSeriesVisibleInLegend(i)) {
                LegendItem item = getLegendItem(index, i);
                if (item != null) {
                    result.add(item);
                }
            }
        }
    }
    else {
        for (int i = seriesCount - 1; i >= 0; i--) {
            if (isSeriesVisibleInLegend(i)) {
                LegendItem item = getLegendItem(index, i);
                if (item != null) {
                    result.add(item);
                }
            }
        }
    }
    return result;
}
