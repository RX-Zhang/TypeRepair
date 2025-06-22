def detect_cycle(node):
    if node is None:  # Added boundary check
        return False
        
    hare = tortoise = node
    while True:
        if hare is None or hare.successor is None:  # Added check for None
            return False
        tortoise = tortoise.successor
        hare = hare.successor.successor
        if hare is tortoise:
            return True
