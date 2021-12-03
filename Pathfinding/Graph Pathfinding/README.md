# Graph Pathfinding
An interactive demo of pathfinding algorithms using traditional graphs.

## Running The Project

This project was created in JCreator using JDK 1.8.

Either import the files into your preferred Java IDE or compile all of the .java files together. Once compiled, "nodeRunner.java" contains the main method to be executed to run the program.

## Description
Once run, displays a blank screen with various buttons along the bottom.

### Buttons
"ADD" will add a node at a random location on the screen with a random color. Using left click on a node will select the node, indicated by a blue outline and cross on the node.

Once at least two nodes have been selected, pressing "CONNECT" will create an edge between the nodes with a default weight of one.
If multiple nodes have been selected, "CONNECT" will form a path from the first node to the last node in the order that the nodes were selected.

"CLEAR" will remove all nodes and egdes from the screen
.
"QBUILD" is a modified verson of "CONNECT" that can be toggled on/off. If toggled (indicated by a blue highlight), selecting two nodes will automatically form an edge between the two nodes allowing for a quicker construction of edges.

"DELETE" will delete any selected nodes and all edges incident to the node.

"PATH" will perform a simple Depth-first search from one selected node to the other selected node, ignoring edge weights. 

Green edges indicate an edge found by the algorithm during the initial search, blue edges indicate an edge considered by the algorithm during the traceback, and red edges/nodes indicates the final path chosen by the algorithm.

"CLEAN" will maintain all nodes and edges, but remove all markings by the pathfinding algorithm.

"EDIT" can be toggled on/off to alter the weights of the edges. If toggled (indicated by a blue highlight), the user can select a weight and edit it using the number keys and the backspace key. 
Once finished editing a weight, the enter key can be pressed to commit the weight.

"DIJKSTRA" is currently unimplemented and does nothing.
