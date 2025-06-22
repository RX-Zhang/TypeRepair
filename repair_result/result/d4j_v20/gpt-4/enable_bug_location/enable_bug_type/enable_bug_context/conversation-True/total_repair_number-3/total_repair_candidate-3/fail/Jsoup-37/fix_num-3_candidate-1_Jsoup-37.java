public String html() {
    StringBuilder accum = new StringBuilder();
    if (preserveWhitespace(this)) {
        html(accum);
        return accum.toString();
    }
    html(accum);
    return accum.toString().trim();
}
