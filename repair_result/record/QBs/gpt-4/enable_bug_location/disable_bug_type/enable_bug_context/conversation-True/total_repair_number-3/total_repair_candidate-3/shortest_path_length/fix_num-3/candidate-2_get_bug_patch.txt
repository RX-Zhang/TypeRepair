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
                current_dist = None
            new_dist = distance + length_by_edge[(node, nextnode)]
            if current_dist is None or new_dist < current_dist:
                insert_or_update(unvisited_nodes, (new_dist, nextnode))
    return float('inf')

def get(node_heap, wanted_node):
    for dist, node in node_heap:
        if node == wanted_node:
            return dist
    return float('inf')

def insert_or_update(node_heap, dist_node):
    dist, node = dist_node
    for i, tpl in enumerate(node_heap):
        a, b = tpl
        if b == node:
            node_heap[i] = dist_node
            heapify(node_heap)
            return None
    heappush(node_heap, dist_node)
    return None
