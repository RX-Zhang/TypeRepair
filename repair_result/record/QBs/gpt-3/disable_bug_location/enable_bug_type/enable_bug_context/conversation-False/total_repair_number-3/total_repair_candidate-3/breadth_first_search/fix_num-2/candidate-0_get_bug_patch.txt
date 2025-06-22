from collections import deque as Queue
def breadth_first_search(startnode, goalnode):
    queue = Queue()
    queue.append(startnode)
    nodesseen = set()
    nodesseen.add(startnode)
    while queue:
        node = queue.popleft()
        if node is goalnode:
            return True
        else:
            # Add only unseen successors to the queue and mark them seen
            for successor in node.successors:
                if successor not in nodesseen:
                    queue.append(successor)
                    nodesseen.add(successor)
    return False
