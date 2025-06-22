public String generateToolTipFragment(String toolTipText) {
    if (toolTipText == null) {
        return " title=\"\" alt=\"\"";
    }
    String escaped = toolTipText.replace("\"", "&quot;");
    return " title=\"" + escaped + "\" alt=\"\"";
}
