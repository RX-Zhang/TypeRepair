def detect_cycle(node):
    if node is None:  # Boundary check for None node
        return False
        
    hare = tortoise = node
    while True:
        if hare is None or hare.successor is None:  # Check for None before accessing successor
            return False
        tortoise = tortoise.successor
        hare = hare.successor.successor
        if hare is tortoise:
            return True
