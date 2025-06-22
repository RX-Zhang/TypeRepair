from heapq import *

def shortest_path_length(length_by_edge, startnode, goalnode):
    unvisited_nodes = []  # min-heap containing (distance, node) pairs
    heappush(unvisited_nodes, (0, startnode))
    visited_nodes = set()
    distance_map = {startnode: 0}
    while unvisited_nodes:
        distance, node = heappop(unvisited_nodes)
        if node is goalnode:
            return distance
        if node in visited_nodes:
            continue
        visited_nodes.add(node)
        for nextnode in node.successors:
            if nextnode in visited_nodes:
                continue
            old_distance = distance_map.get(nextnode, float('inf'))
            new_distance = distance + length_by_edge.get((node, nextnode), float('inf'))
            if new_distance < old_distance:
                distance_map[nextnode] = new_distance
                heappush(unvisited_nodes, (new_distance, nextnode))
    return float('inf')
