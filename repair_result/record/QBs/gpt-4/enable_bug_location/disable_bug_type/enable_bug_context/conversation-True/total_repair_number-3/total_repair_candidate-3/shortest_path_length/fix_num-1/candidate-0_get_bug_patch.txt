from heapq import *
def shortest_path_length(length_by_edge, startnode, goalnode):
    unvisited_nodes = [] # FibHeap containing (distance, node) pairs
    heappush(unvisited_nodes, (0, startnode))
    visited_nodes = set()
    while len(unvisited_nodes) > 0:
        distance, node = heappop(unvisited_nodes)
        if node is goalnode:
            return distance
        if node in visited_nodes:
            continue
        visited_nodes.add(node)
        for nextnode in node.successors:
            if nextnode in visited_nodes:
                continue
            current_dist = get(unvisited_nodes, nextnode)
            if current_dist == float('inf'):
                new_dist = distance + length_by_edge.get((node, nextnode), float('inf'))
            else:
                new_dist = min(current_dist, distance + length_by_edge.get((node, nextnode), float('inf')))
            insert_or_update(unvisited_nodes, (new_dist, nextnode))
    return float('inf')

def get(node_heap, wanted_node):
    for dist, node in node_heap:
        if node == wanted_node:
            return dist
    return float('inf')

def insert_or_update(node_heap, dist_node):
    dist, node = dist_node
    for i, (a, b) in enumerate(node_heap):
        if b == node:
            if dist < a:
                node_heap[i] = dist_node
                heapify(node_heap)
            return None
    heappush(node_heap, dist_node)
    return None
