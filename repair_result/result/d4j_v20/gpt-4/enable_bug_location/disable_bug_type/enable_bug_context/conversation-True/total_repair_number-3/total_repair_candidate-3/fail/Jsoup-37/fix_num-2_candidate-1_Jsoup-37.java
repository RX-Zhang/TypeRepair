public String html() {
    StringBuilder accum = new StringBuilder();
    html(accum);
    if (!preserveWhitespace(this)) {
        return accum.toString().trim();
    }
    return accum.toString();
}
