def hanoi(height, start=1, end=3):
    steps = []
    if height > 0:
        helper = ({1, 2, 3} - {start} - {end}).pop()
        steps.extend(hanoi(height - 1, start, end=helper))  # Fix: change helper to end
        steps.append((start, end))  # Fix: change helper to end
        steps.extend(hanoi(height - 1, start=helper, end=end))  # Fix: swap start and end
    return steps
