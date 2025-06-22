public String html() {
    StringBuilder accum = new StringBuilder();
    html(accum);
    return accum.toString(); // Remove the trim() to preserve whitespace
}
