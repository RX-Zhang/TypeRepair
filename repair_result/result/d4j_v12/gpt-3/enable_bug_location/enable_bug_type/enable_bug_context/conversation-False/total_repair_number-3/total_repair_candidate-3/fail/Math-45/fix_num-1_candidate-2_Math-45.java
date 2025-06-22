public OpenMapRealMatrix(int rowDimension, int columnDimension) {
    if (rowDimension <= 0 || columnDimension <= 0 || rowDimension > Integer.MAX_VALUE || columnDimension > Integer.MAX_VALUE) {
        throw new org.apache.commons.math.exception.NumberIsTooLargeException("Row or column dimension is out of valid range.");
    }
    
    this.rows = rowDimension;
    this.columns = columnDimension;
    this.entries = new OpenIntToDoubleHashMap(0.0);
}
