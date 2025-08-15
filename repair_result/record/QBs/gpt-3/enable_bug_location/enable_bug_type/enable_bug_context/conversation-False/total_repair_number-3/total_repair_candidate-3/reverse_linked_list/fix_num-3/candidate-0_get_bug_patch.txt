def reverse_linked_list(node):
    prevnode = None
    current = node  # Use a separate variable to traverse the list
    while current:
        nextnode = current.successor
        current.successor = prevnode
        prevnode = current  # Move prevnode to current
        current = nextnode  # Move to the next node
    return prevnode
