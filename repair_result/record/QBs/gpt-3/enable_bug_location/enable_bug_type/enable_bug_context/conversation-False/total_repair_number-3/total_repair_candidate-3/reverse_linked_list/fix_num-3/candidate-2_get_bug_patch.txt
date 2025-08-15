def reverse_linked_list(node):
    prevnode = None
    currentnode = node  # Use a separate variable for the current node
    while currentnode:  # Iterate through the list using currentnode
        nextnode = currentnode.successor
        currentnode.successor = prevnode
        prevnode = currentnode  # Move prevnode to currentnode
        currentnode = nextnode  # Move to the next node
    return prevnode  # This will be the new head of the reversed list
