from collections import deque as Queue

def breadth_first_search(startnode, goalnode):
    queue = Queue()
    queue.append(startnode)
    nodesseen = set()
    nodesseen.add(startnode)
    
    while queue:  # Ensure the loop continues only while there are nodes in the queue
        node = queue.popleft()
        if node is goalnode:
            return True
        else:
            for successor in node.successors:
                if successor not in nodesseen:  # Check if the successor has not been seen
                    queue.append(successor)  # Use append instead of extend for single node
                    nodesseen.add(successor)  # Add the successor to the seen set
    return False
