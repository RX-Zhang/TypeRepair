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
            for succ in node.successors:
                if succ not in nodesseen:
                    queue.append(succ)
                    nodesseen.add(succ)
    return False
