from heapq import *
def shortest_path_length(length_by_edge, startnode, goalnode):
    unvisited_nodes = [] # heap containing (distance, node) pairs
    heappush(unvisited_nodes, (0, startnode))
    visited_nodes = set()
    dist_map = {startnode: 0}
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
            new_dist = distance + length_by_edge[(node, nextnode)]
            old_dist = dist_map.get(nextnode, float('inf'))
            if new_dist < old_dist:
                dist_map[nextnode] = new_dist
                heappush(unvisited_nodes, (new_dist, nextnode))
    return float('inf')

