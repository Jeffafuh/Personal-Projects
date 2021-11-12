An interactive demo on pathfinding algorithms on a grid.

Once compiled, "pathFinder.java" contains the main method to be executed to run the program. Once run, displays a grid of blank tiles.
Left clicking (or drag left click) can be used to create "walls/obstacles" on the grid, indicated by a red tile.
Right clicking can be used to set the start/end points for the pathfinding algorithms.

Displayed on the bottom are four buttons used to manipulate the grid. "CLEAR" empties the entire grid to a blank state and clears all marks.
"CLEAN" retains all walls and start/end points, but clears all paths generated.
"FLOOD" performs a variation of Dijkstra's algorithm where all tiles share a weight of 1 (moves in the 4 cardinal directions).
"GREED" performs a greedy algorithm that always takes the step that minimizes the distance from the start and end point (moves in all 8 cardinal directions).

Created in JCreator.
