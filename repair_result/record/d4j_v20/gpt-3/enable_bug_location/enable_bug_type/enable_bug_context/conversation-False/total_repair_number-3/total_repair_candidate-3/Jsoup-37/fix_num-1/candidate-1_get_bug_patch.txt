public String html() {
    StringBuilder accum = new StringBuilder();
    html(accum);
    return accum.toString(); // Removed trim() to preserve whitespace as intended
}
