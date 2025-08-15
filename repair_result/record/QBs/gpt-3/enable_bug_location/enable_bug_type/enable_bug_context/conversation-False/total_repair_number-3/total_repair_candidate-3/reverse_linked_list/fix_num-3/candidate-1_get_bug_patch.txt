def reverse_linked_list(node):
    prevnode = None
    current = node  # Use a separate variable to keep track of the current node
    while current:
        nextnode = current.successor
        current.successor = prevnode
        prevnode = current  # Move prevnode to the current node
        current = nextnode  # Move to the next node
    return prevnode
