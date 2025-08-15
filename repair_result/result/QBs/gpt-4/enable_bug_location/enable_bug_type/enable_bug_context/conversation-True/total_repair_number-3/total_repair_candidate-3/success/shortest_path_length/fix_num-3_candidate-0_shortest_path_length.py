from heapq import *
def shortest_path_length(length_by_edge, startnode, goalnode):
    unvisited_nodes = [] # Heap containing (distance, node) pairs
    heappush(unvisited_nodes, (0, startnode))
    distances = {startnode: 0}
    visited_nodes = set()
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
            edge_length = length_by_edge.get((node, nextnode), float('inf'))
            new_dist = distance + edge_length
            old_dist = distances.get(nextnode, float('inf'))
            if new_dist < old_dist:
                distances[nextnode] = new_dist
                heappush(unvisited_nodes, (new_dist, nextnode))
    return float('inf')
