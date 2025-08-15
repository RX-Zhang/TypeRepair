private void visitParameterList(NodeTraversal t, Node call,
    FunctionType functionType) {
  Iterator<Node> arguments = call.children().iterator();
  arguments.next(); // skip the function name

  Iterator<Node> parameters = functionType.getParameters().iterator();
  int ordinal = 0;
  Node parameter = null;
  Node argument = null;
  while (arguments.hasNext()) {
    if (parameters.hasNext()) {
      parameter = parameters.next();
    } else if (!functionType.isVarArgs()) {
      // No more parameters and function is not var_args: break early to avoid false matches
      break;
    }
    argument = arguments.next();
    ordinal++;

    JSType parameterType = parameter != null ? getJSType(parameter) : null;
    JSType argumentType = getJSType(argument);

    validator.expectArgumentMatchesParameter(t, argument,
        argumentType, parameterType, call, ordinal);
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
