public String html() {
    StringBuilder accum = new StringBuilder();
    html(accum);
    // Ensure that we do not trim the leading whitespace that is part of the HTML structure
    return accum.toString(); 
}
