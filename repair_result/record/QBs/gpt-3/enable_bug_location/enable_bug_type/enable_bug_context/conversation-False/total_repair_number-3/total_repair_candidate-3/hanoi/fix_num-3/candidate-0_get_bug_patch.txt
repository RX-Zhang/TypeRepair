def hanoi(height, start=1, end=3):
    steps = []
    if height > 0:
        helper = ({1, 2, 3} - {start} - {end}).pop()
        steps.extend(hanoi(height - 1, start, end))  # Corrected the order of parameters
        steps.append((start, end))  # Corrected the move to append the correct end
        steps.extend(hanoi(height - 1, helper, end))
    return steps
