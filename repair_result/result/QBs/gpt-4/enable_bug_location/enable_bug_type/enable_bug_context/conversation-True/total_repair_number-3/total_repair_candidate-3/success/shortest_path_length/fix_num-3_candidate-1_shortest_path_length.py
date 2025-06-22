from heapq import *
def shortest_path_length(length_by_edge, startnode, goalnode):
    unvisited_nodes = [] # heap containing (distance, node) pairs
    heappush(unvisited_nodes, (0, startnode))
    visited_nodes = set()
    distances = {startnode: 0}
    while len(unvisited_nodes) > 0:
        distance, node = heappop(unvisited_nodes)
        if node in visited_nodes:
            continue
        if node is goalnode:
            return distance
        visited_nodes.add(node)
        for nextnode in node.successors:
            if nextnode in visited_nodes:
                continue
            new_dist = distance + length_by_edge.get((node, nextnode), float('inf'))
            if new_dist < distances.get(nextnode, float('inf')):
                distances[nextnode] = new_dist
                heappush(unvisited_nodes, (new_dist, nextnode))
    return float('inf')
