# ex1

hello:) there are two folders in here:

src:
contains a few java files
there are 3 implemants class
and 2 graph class (3 because the node class is in one of them)

WGraph_DS:
contains all the build of a weighted graph 
incloding the node class

each graph has two hashmaps
1.repesnting all the nodes of the graph
2.represnting all the edges in the graph with anther hashmap in it 
each node has a row and there- he has a hashmap with each node he is neighbor with and the edge weight

we have a few functions in here:
 getNode(int key)-returns a node from the graph
 hasEdge(int node1, int node2)-checks if there is an edge between them
 getEdge(int node1, int node2)-return the weight of the edge between two nodes
 addNode(int key)- add node to the graph
 connect(int node1, int node2, double w)- connects two nodes
 getV()-returns a list of all the graph nodes
 getV(int node_id)-returns a list of the node neighbors
 removeNode(int key)-removes a node
 removeEdge(int node1, int node2)-removes a edge
 nodeSize()-returns the number of nodes in the graph
 edgeSize()-returns the number of edges in the graph
 getMC()-returns the number of changes this graph had
 equals(Object obj)- a deep check if two graph are equals
 


WGraph_Algo:
using the code from WGraph_DS here we are doing some fun stuff with the graph 
like checking if he is connected saving and oding graph to a file and more:)

