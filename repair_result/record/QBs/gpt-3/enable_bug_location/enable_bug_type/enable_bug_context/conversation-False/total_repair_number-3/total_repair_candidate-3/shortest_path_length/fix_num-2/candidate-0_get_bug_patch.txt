from heapq import *
def shortest_path_length(length_by_edge, startnode, goalnode):
    unvisited_nodes = [] # FibHeap containing (node, distance) pairs
    heappush(unvisited_nodes, (0, startnode))
    visited_nodes = set()
    while len(unvisited_nodes) > 0:
        distance, node = heappop(unvisited_nodes)
        if node is goalnode:
            return distance
        visited_nodes.add(node)
        for nextnode in node.successors:
            if nextnode in visited_nodes:
                continue
            # Ensure that we are correctly calculating the distance
            new_distance = distance + length_by_edge.get((node, nextnode), float('inf'))
            insert_or_update(unvisited_nodes, (new_distance, nextnode))
    return float('inf')

def get(node_heap, wanted_node):
    for dist, node in node_heap:
        if node == wanted_node:
            return dist
    return float('inf')  # Return inf if the node is not found

def insert_or_update(node_heap, dist_node):
    dist, node = dist_node
    for i, tpl in enumerate(node_heap):
        a, b = tpl
        if b == node:
            node_heap[i] = dist_node #heapq retains sorted property
            return None
    heappush(node_heap, dist_node)
    return None
