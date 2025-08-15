private void visitParameterList(NodeTraversal t, Node call,
    FunctionType functionType) {
  Iterator<Node> arguments = call.children().iterator();
  arguments.next(); // skip the function name

  Iterator<Node> parameters = functionType.getParameters().iterator();
  int ordinal = 0;
  Node parameter = null;
  Node argument = null;

  // Iterate over all arguments, not limited by number of parameters,
  // to properly handle var_args and extra arguments.
  while (arguments.hasNext()) {
    argument = arguments.next();
    ordinal++;

    if (parameters.hasNext()) {
      parameter = parameters.next();
    } else {
      // If no parameters left, treat as var_args parameter
      if (!functionType.isVariadic()) {
        // Not variadic, so we break out to check argument count later
        break;
      }
      // Use the last parameter as the var_args parameter type
      // (var_args is represented by last parameter repeated)
      if (parameter == null) {
        // Defensive: no parameters at all, so no var_args either
        break;
      }
    }

    validator.expectArgumentMatchesParameter(t, argument,
        getJSType(argument), getJSType(parameter), call, ordinal);
  }

  int numArgs = call.getChildCount() - 1;
  int minArgs = functionType.getMinArguments();
  int maxArgs = functionType.getMaxArguments();
  if (minArgs > numArgs || maxArgs < numArgs) {
    report(t, call, WRONG_ARGUMENT_COUNT,
            validator.getReadableJSTypeName(call.getFirstChild(), false),
            String.valueOf(numArgs), String.valueOf(minArgs),
            maxArgs != Integer.MAX_VALUE ?
            " and no more than " + maxArgs + " argument(s)" : "");
  }
}
