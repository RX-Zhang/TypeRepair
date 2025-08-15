from heapq import *

def shortest_path_length(length_by_edge, startnode, goalnode):
    unvisited_nodes = []  # min-heap containing (distance, node) pairs
    heappush(unvisited_nodes, (0, startnode))
    visited_nodes = set()
    distances = {startnode: 0}  # Keep track of best distances

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
            # Calculate new potential distance
            new_distance = distance + length_by_edge[(node, nextnode)]
            # If this distance is better, update and push into heap
            if new_distance < distances.get(nextnode, float('inf')):
                distances[nextnode] = new_distance
                heappush(unvisited_nodes, (new_distance, nextnode))

    return float('inf')

# The functions get and insert_or_update are no longer needed
