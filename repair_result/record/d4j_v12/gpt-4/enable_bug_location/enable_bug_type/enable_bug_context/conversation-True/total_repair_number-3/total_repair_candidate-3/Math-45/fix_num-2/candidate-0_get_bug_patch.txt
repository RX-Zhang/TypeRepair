public OpenMapRealMatrix(int rowDimension, int columnDimension) {
    if (rowDimension < 0) {
        throw new org.apache.commons.math.exception.NotStrictlyPositiveException(rowDimension);
    }
    if (columnDimension < 0) {
        throw new org.apache.commons.math.exception.NotStrictlyPositiveException(columnDimension);
    }
    if ((long) rowDimension * (long) columnDimension > Integer.MAX_VALUE) {
        throw new org.apache.commons.math.exception.NumberIsTooLargeException((long) rowDimension * (long) columnDimension, Integer.MAX_VALUE, true);
    }
    super(rowDimension, columnDimension);
    this.rows = rowDimension;
    this.columns = columnDimension;
    this.entries = new OpenIntToDoubleHashMap(0.0);
}
