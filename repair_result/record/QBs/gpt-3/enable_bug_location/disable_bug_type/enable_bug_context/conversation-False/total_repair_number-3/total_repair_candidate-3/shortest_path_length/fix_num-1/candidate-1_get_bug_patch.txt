from heapq import *
def shortest_path_length(length_by_edge, startnode, goalnode):
    unvisited_nodes = []  # heap containing (distance, node) pairs
    heappush(unvisited_nodes, (0, startnode))
    visited_nodes = set()
    distances = {startnode: 0}  # store shortest known distance for each node

    while unvisited_nodes:
        distance, node = heappop(unvisited_nodes)
        if node in visited_nodes:
            continue
        if node is goalnode:
            return distance
        visited_nodes.add(node)

        for nextnode in node.successors:
            if nextnode in visited_nodes:
                continue
            current_distance = distances.get(nextnode, float('inf'))
            new_distance = distance + length_by_edge.get((node, nextnode), float('inf'))
            if new_distance < current_distance:
                distances[nextnode] = new_distance
                heappush(unvisited_nodes, (new_distance, nextnode))

    return float('inf')
