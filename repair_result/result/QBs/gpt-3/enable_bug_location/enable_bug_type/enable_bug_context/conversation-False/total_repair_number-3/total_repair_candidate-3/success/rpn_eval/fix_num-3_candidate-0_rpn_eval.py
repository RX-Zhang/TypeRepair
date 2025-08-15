def rpn_eval(tokens):
    def op(symbol, a, b):
        return {
            '+': lambda a, b: a + b,
            '-': lambda a, b: b - a,  # Corrected the order of operands for subtraction
            '*': lambda a, b: a * b,
            '/': lambda a, b: b / a   # Corrected the order of operands for division
        }[symbol](a, b)
    
    stack = []
    for token in tokens:
        if isinstance(token, float):
            stack.append(token)
        else:
            if len(stack) < 2:  # Added boundary check to prevent runtime error
                raise ValueError("Insufficient values in the stack for operation.")
            a = stack.pop()
            b = stack.pop()
            stack.append(
                op(token, a, b)
            )
    if len(stack) != 1:  # Added check to ensure only one result remains
        raise ValueError("The user input has too many values.")
    return stack.pop()
