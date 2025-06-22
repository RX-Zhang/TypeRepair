public String generateToolTipFragment(String toolTipText) {
    String escapedText = toolTipText.replace("\"", "&quot;");
    return " title=\"" + escapedText + "\" alt=\"\"";
}
