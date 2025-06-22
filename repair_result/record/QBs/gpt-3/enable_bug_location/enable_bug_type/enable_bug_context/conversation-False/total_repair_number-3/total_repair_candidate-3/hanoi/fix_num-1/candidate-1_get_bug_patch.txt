def hanoi(height, start=1, end=3):
    steps = []
    if height > 0:
        helper = (set([1, 2, 3]) - {start} - {end}).pop()
        steps.extend(hanoi(height - 1, start, end))  # Corrected order of parameters
        steps.append((start, end))  # Corrected to move directly to end
        steps.extend(hanoi(height - 1, helper, start))  # Corrected order of parameters
    return steps
