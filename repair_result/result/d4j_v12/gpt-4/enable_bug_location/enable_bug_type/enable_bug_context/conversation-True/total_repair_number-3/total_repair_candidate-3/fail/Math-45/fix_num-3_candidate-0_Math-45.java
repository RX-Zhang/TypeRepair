public OpenMapRealMatrix(int rowDimension, int columnDimension) {
    if (rowDimension < 0 || columnDimension < 0) {
        throw new org.apache.commons.math.exception.NotPositiveException();
    }
    if (rowDimension > Integer.MAX_VALUE || columnDimension > Integer.MAX_VALUE) {
        throw new org.apache.commons.math.exception.NumberIsTooLargeException(rowDimension > Integer.MAX_VALUE ? rowDimension : columnDimension, Integer.MAX_VALUE, true);
    }
    super(rowDimension, columnDimension);
    this.rows = rowDimension;
    this.columns = columnDimension;
    this.entries = new OpenIntToDoubleHashMap(0.0);
}
