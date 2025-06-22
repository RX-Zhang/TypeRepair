public String generateToolTipFragment(String toolTipText) {
    if (toolTipText == null) {
        toolTipText = "";
    }
    String escaped = toolTipText.replace("\"", "&quot;");
    return " title=\"" + escaped + "\" alt=\"\"";
}
