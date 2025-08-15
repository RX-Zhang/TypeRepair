public String generateToolTipFragment(String toolTipText) {
    if (toolTipText == null) {
        return " title=\"\" alt=\"\"";
    }
    String escapedText = toolTipText.replace("\"", "&quot;");
    return " title=\"" + escapedText + "\" alt=\"\"";
}
