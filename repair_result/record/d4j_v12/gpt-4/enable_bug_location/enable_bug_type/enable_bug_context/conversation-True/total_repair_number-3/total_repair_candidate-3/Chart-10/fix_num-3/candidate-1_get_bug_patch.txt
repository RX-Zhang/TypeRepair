public String generateToolTipFragment(String toolTipText) {
    if (toolTipText == null) {
        return "";
    }
    String escaped = toolTipText.replace("&", "&amp;")
                                .replace("\"", "&quot;")
                                .replace("<", "&lt;")
                                .replace(">", "&gt;");
    return " title=\"" + escaped + "\" alt=\"\"";
}
