def hanoi(height, start=1, end=3):
    steps = []
    if height > 0:
        helper = ({1, 2, 3} - {start} - {end}).pop()
        steps.extend(hanoi(height - 1, start, end=helper))  # Changed argument order
        steps.append((start, end))  # Changed the move from (start, helper) to (start, end)
        steps.extend(hanoi(height - 1, helper, end))
    return steps
