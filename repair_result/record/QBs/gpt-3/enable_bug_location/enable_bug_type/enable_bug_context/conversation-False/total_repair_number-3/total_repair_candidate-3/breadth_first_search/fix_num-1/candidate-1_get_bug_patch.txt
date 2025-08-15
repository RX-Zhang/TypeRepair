from collections import deque as Queue

def breadth_first_search(startnode, goalnode):
    queue = Queue()
    queue.append(startnode)
    nodesseen = set()
    nodesseen.add(startnode)

    while queue:  # Check if the queue is not empty
        node = queue.popleft()
        if node is goalnode:
            return True
        else:
            for successor in node.successors:
                if successor not in nodesseen:
                    queue.append(successor)  # Use append instead of extend
                    nodesseen.add(successor)  # Add successor to seen set
    return False
