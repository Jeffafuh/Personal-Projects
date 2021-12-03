# Grid Pathfinding Demo
An interactive demo of pathfinding algorithms on a grid.

## Running the Project
This project was created in JCreator as a workspace using JDK 1.8.

Either import the workspace in JCreator or compile all of the .java files together located in /src/. Once compiled, "pathFinder.java" contains the main method to be executed to run the program.

## Description
Once run, displays a grid of blank tiles.

Left clicking (or drag left click) can be used to create "walls/obstacles" on the grid, indicated by a red tile.

Right clicking can be used to set the start/end points for the pathfinding algorithms.

### Buttons
Displayed on the bottom are four buttons used to manipulate the grid. 

"CLEAR" empties the entire grid to a blank state and clears all marks.

"CLEAN" retains all walls and start/end points, but clears all paths generated.

"FLOOD" performs a variation of Dijkstra's algorithm where all tiles share a weight of 1 (moves in the 4 cardinal directions).

"GREED" performs a greedy algorithm that always takes the step that minimizes the distance from the start and end point (moves in all 8 cardinal directions).
