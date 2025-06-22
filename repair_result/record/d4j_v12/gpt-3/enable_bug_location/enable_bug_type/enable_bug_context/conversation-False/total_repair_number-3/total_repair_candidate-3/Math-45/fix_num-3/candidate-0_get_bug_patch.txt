public OpenMapRealMatrix(int rowDimension, int columnDimension) {
    if (rowDimension <= 0 || columnDimension <= 0 || columnDimension > Integer.MAX_VALUE) {
        throw new org.apache.commons.math.exception.NumberIsTooLargeException(columnDimension, Integer.MAX_VALUE, true);
    }
    this.rows = rowDimension;
    this.columns = columnDimension;
    this.entries = new OpenIntToDoubleHashMap(0.0);
}
