public String html() {
    StringBuilder accum = new StringBuilder();
    if (preserveWhitespace(this)) {
        html(accum);
        return accum.toString();
    } else {
        html(accum);
        return accum.toString().trim();
    }
}
