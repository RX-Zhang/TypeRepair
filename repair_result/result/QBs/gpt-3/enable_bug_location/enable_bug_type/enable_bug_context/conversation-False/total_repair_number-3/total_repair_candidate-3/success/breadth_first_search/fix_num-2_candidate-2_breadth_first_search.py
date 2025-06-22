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
            for successor in node.successors:  # Iterate through successors
                if successor not in nodesseen:
                    queue.append(successor)  # Append successor to the queue
                    nodesseen.add(successor)  # Mark as seen

    return False
