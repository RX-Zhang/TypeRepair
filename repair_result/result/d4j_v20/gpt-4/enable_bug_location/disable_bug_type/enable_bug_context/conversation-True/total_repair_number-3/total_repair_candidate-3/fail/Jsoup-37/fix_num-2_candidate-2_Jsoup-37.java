public String html() {
    StringBuilder accum = new StringBuilder();
    html(accum);
    if (!Element.preserveWhitespace(this)) {
        return accum.toString().trim();
    } else {
        return accum.toString();
    }
}
