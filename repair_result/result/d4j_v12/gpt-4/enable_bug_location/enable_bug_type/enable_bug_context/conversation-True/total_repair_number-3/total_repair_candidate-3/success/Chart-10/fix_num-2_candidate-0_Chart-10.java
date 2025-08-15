public String generateToolTipFragment(String toolTipText) {
    if (toolTipText == null) {
        toolTipText = "";
    }
    StringBuilder fragment = new StringBuilder();
    fragment.append(" title=\"");
    for (int i = 0; i < toolTipText.length(); i++) {
        char c = toolTipText.charAt(i);
        switch (c) {
            case '"':
                fragment.append("&quot;");
                break;
            case '&':
                fragment.append("&amp;");
                break;
            case '<':
                fragment.append("&lt;");
                break;
            case '>':
                fragment.append("&gt;");
                break;
            default:
                fragment.append(c);
        }
    }
    fragment.append("\" alt=\"\"");
    return fragment.toString();
}
