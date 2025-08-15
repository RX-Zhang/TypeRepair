def reverse_linked_list(node):
    if node is None:  # Adding boundary check for None input
        return None

    prevnode = None
    while node:
        nextnode = node.successor
        node.successor = prevnode
        prevnode = node  # Correcting the logic to keep track of the previous node
        node = nextnode
    return prevnode
