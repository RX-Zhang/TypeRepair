public String html() {
    StringBuilder accum = new StringBuilder();
    html(accum);
    return accum.toString(); // Removed the trim() to preserve whitespace
}
