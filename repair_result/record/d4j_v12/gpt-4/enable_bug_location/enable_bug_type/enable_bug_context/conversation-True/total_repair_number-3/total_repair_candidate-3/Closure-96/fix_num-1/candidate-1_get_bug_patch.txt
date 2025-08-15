private void visitParameterList(NodeTraversal t, Node call,
    FunctionType functionType) {
  Iterator<Node> arguments = call.children().iterator();
  arguments.next(); // skip the function name

  Iterator<Node> parameters = functionType.getParameters().iterator();
  int ordinal = 0;
  Node parameter = null;
  Node argument = null;

  if (!parameters.hasNext() && arguments.hasNext()) {
    // This means the function is declared with var_args and no explicit parameters.
    parameter = null;
  }

  while (arguments.hasNext()) {
    argument = arguments.next();
    ordinal++;

    if (parameters.hasNext()) {
      parameter = parameters.next();
    } else {
      parameter = null;
    }

    validator.expectArgumentMatchesParameter(
        t,
        argument,
        getJSType(argument),
        parameter == null ? functionType.getVarArgsType() : getJSType(parameter),
        call,
        ordinal);
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
