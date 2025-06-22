final void html(final Appendable accum, final Document.OutputSettings out) throws IOException {
    final int sz = size;
    for (int i = 0; i < sz; i++) {
        // inlined from Attribute.html()
        final String key = keys[i];
        final String val = vals[i];
        accum.append(' ').append(key);

        // collapse for boolean attributes: if output syntax is html and 
        // val is null OR (val is empty or val equals key and attribute is boolean), then omit =""
        if (!(out.syntax() == Document.OutputSettings.Syntax.html
            && (val == null || val.isEmpty() || (val.equals(key) && Attribute.isBooleanAttribute(key))))) {
            accum.append("=\"");
            Entities.escape(accum, val == null ? EmptyString : val, out, true, false, false);
            accum.append('"');
        }
    }
}
