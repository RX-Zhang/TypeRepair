public String generateToolTipFragment(String toolTipText) {
    // Escape double quotes for HTML
    String escapedText = toolTipText.replace("\"", "&quot;");
    return " title=\"" + escapedText + "\" alt=\"\"";
}
