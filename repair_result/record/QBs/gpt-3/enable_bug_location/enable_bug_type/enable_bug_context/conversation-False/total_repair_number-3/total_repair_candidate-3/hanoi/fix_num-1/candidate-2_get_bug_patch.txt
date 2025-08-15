def hanoi(height, start=1, end=3):
    steps = []
    if height > 0:
        helper = ({1, 2, 3} - {start} - {end}).pop()
        steps.extend(hanoi(height - 1, start, end))  # Changed parameters to fix logical error
        steps.append((start, end))  # Changed helper to end to fix logical error
        steps.extend(hanoi(height - 1, helper, end))
    return steps
