public String generateToolTipFragment(String toolTipText) {
    String escaped = toolTipText.replace("\"", "&quot;");
    return " title=\"" + escaped
        + "\" alt=\"\"";
}
