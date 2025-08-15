public String html() {
    StringBuilder accum = new StringBuilder();
    if (preserveWhitespace(this))
        html(accum);
    else
        html(accum);
    return accum.toString();
}
