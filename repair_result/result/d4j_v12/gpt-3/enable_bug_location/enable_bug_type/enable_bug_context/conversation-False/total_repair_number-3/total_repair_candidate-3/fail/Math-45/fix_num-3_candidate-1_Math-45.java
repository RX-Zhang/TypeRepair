public OpenMapRealMatrix(int rowDimension, int columnDimension) {
    // Input validation
    if (rowDimension <= 0) {
        throw new IllegalArgumentException("Row dimension must be positive.");
    }
    if (columnDimension <= 0 || columnDimension > Integer.MAX_VALUE) {
        throw new IllegalArgumentException("Column dimension must be positive and less than or equal to Integer.MAX_VALUE.");
    }
    
    this.rows = rowDimension;
    this.columns = columnDimension;
    this.entries = new OpenIntToDoubleHashMap(0.0);
}
