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
            unvisited = [n for n in node.successors if n not in nodesseen]
            queue.extend(unvisited)
            nodesseen.update(unvisited)
    return False
