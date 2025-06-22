static boolean evaluatesToLocalValue(Node value, Predicate<Node> locals) {
  switch (value.getType()) {
    case Token.ASSIGN:
      // A result that is aliased by a non-local name, is the effectively the
      // same as returning a non-local name, but this doesn't matter if the
      // value is immutable.
      // Use isAssignmentOp(Node) here instead of locals.apply directly on Node,
      // and properly handle the left and right sides.
      return isImmutableValue(value.getLastChild())
          || (locals.apply(value.getFirstChild())
              && evaluatesToLocalValue(value.getLastChild(), locals));
    case Token.COMMA:
      return evaluatesToLocalValue(value.getLastChild(), locals);
    case Token.AND:
    case Token.OR:
      return evaluatesToLocalValue(value.getFirstChild(), locals)
         && evaluatesToLocalValue(value.getLastChild(), locals);
    case Token.HOOK:
      // The operands of a hook (conditional) where only the two options need to be checked,
      // the condition can be anything.
      return evaluatesToLocalValue(value.getFirstChild().getNext(), locals)
         && evaluatesToLocalValue(value.getLastChild(), locals);
    case Token.INC:
    case Token.DEC:
      // If increment/decrement is postfix (has INCRDECR_PROP set),
      // it evaluates to the old value (non-local if variable is non-local)
      // Otherwise it's the updated value (local)
      if (value.getBooleanProp(Node.INCRDECR_PROP)) {
        return evaluatesToLocalValue(value.getFirstChild(), locals);
      } else {
        return true;
      }
    case Token.THIS:
      return locals.apply(value);
    case Token.NAME:
      return isImmutableValue(value) || locals.apply(value);
    case Token.GETELEM:
    case Token.GETPROP:
      // There is no information about the locality of object properties.
      // We can only trust locals for the entire node.
      return locals.apply(value);
    case Token.CALL:
      return callHasLocalResult(value)
          || isToStringMethodCall(value)
          || locals.apply(value);
    case Token.NEW:
      // TODO(nicksantos): This needs to be changed so that it
      // returns true iff we're sure the value was never aliased from inside
      // the constructor (similar to callHasLocalResult)
      // Correct handling: check if constructor call has side effects.
      // Since no compiler is passed, we conservatively return false here.
      return false;
    case Token.FUNCTION:
    case Token.REGEXP:
    case Token.ARRAYLIT:
    case Token.OBJECTLIT:
      // Literal objects with non-literal children are allowed.
      return true;
    case Token.IN:
      // A simple operator always considered local.
      return true;
    default:
      // Other op force a local value:
      //  x = '' + g (x is now an local string)
      //  x -= g (x is now an local number)
      if (isAssignmentOp(value)
          || isSimpleOperator(value)
          || isImmutableValue(value)) {
        return true;
      }

      throw new IllegalStateException(
          "Unexpected expression node" + value +
          "\n parent:" + value.getParent());
  }
}
