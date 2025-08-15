private void visitParameterList(NodeTraversal t, Node call,
    FunctionType functionType) {
  Iterator<Node> arguments = call.children().iterator();
  arguments.next(); // skip the function name

  Iterator<Node> parameters = functionType.getParameters().iterator();
  int ordinal = 0;
  Node parameter = null;
  Node argument = null;
  while (arguments.hasNext() &&
         parameters.hasNext()) {
    parameter = parameters.next();
    argument = arguments.next();
    ordinal++;

    // Ensure the argument and parameter types are validated
    JSType argumentType = getJSType(argument);
    JSType parameterType = getJSType(parameter);
    
    // Validate argument types
    if (!argumentType.isSubtype(parameterType)) {
      validator.expectArgumentMatchesParameter(t, argument, argumentType, parameterType, call, ordinal);
    }
  }

  int numArgs = call.getChildCount() - 1;
  int minArgs = functionType.getMinArguments();
  int maxArgs = functionType.getMaxArguments();
  if (numArgs < minArgs || numArgs > maxArgs) {
    report(t, call, WRONG_ARGUMENT_COUNT,
            validator.getReadableJSTypeName(call.getFirstChild(), false),
            String.valueOf(numArgs), String.valueOf(minArgs),
            maxArgs != Integer.MAX_VALUE ?
            " and no more than " + maxArgs + " argument(s)" : "");
  }
}
