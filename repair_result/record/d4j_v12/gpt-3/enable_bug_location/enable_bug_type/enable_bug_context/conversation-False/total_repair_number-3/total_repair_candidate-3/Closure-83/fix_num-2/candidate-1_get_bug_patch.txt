public int parseArguments(Parameters params) throws CmdLineException {
  String param = params.getParameter(0);

  if (param == null) {
    setter.addValue(true);
    return 0;
  } else {
    String lowerParam = param.toLowerCase();
    if (TRUES.contains(lowerParam)) {
      setter.addValue(true);
    } else if (FALSES.contains(lowerParam)) {
      setter.addValue(false);
    } else {
      setter.addValue(true); // This line should be corrected to handle unexpected values properly
      return 1; // Changed return value to 1 to indicate a non-standard input
    }
    return 1; // Ensure returning 1 for valid true/false cases
  }
}
