package sjsu.kamel.cs146.project2;

import java.util.*;

/**
 * Maze.java
 * @authors Sebrianne Ferguson and Adham Kamel
 */

public class Maze {

	Coordinate[][] grid;
	Stack<Coordinate> cellStack;
	int totalCells;
	Coordinate currentCell;
	int visitedCells;
	private Random myRandGen; // from instructions
	ArrayList<Coordinate> marked; 
	int time; //for dfs

	/**
	 * ctor for the class
	 * @author Sebrianne Ferguson and Adham Kamel
	 * @param size - the size of the grid
	 */
	public Maze(int size) {
		// create a CellStack (LIFO) to hold a list of cell locations
		grid = new Coordinate[size][size]; // makes a square grid
		totalCells = size * size; // set TotalCells= number of cells in grid
		cellStack = new Stack<Coordinate>(); //for creating the maze
		myRandGen = new Random(0); // seed is 0 -- from instructions
		marked = new ArrayList<Coordinate>(); //for BFS to find the path
		time = 0; //for the depth first search
	}

	/**
	 * myrandom() 
	 * @author Sebrianne Ferguson
	 * from instructions
	 */
	double myrandom() {
		return myRandGen.nextDouble(); // random in 0-1
	}

	/**
	 * generateGrid() 
	 * @author Sebrianne Ferguson
	 * creates a 2D array of Coordinates and generates neighbors for each coordinate.
	 */
	public void generateGrid() {
		//creates new coordinates
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = new Coordinate(i, j);
			}
		}

		// generate the neighbors
		// initially i had the call inside of the previous nested for loop
		// but that generated errors.
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				generateNeighbors(grid[i][j]); 
			}
		}
	}

	/**
	 * @author Sebrianne Ferguson
	 * generateNeighbors() 
	 * generates all the neighboring coordinates to the
	 * given coordinate
	 * @param c- the given coordinate
	 */
	public void generateNeighbors(Coordinate c) {
		if (c.getX() - 1 >= 0) { // determine if c has a western neighbor
			c.addNeighbor(grid[c.getX() - 1][c.getY()]);
		}
		if (c.getX() + 1 < grid.length) { // determine if c has an eastern neighbor
			c.addNeighbor(grid[c.getX() + 1][c.getY()]);
		}
		if (c.getY() - 1 >= 0) { // determine if c has a northern neighbor
			c.addNeighbor(grid[c.getX()][c.getY() - 1]);
		}
		if (c.getY() + 1 < grid.length) { // determine if c has a southern neighbor
			c.addNeighbor(grid[c.getX()][c.getY() + 1]);
		}
	}

	/**
	 * generateMaze() 
	 * @author Sebrianne Ferguson
	 * creates the maze as described in the instructions.
	 */
	public void generateMaze() {
		// choose the starting cell and call it current cell.
		currentCell = grid[0][0];
		visitedCells = 1; 
		while (visitedCells < totalCells) { 
			// find all neighbors of CurrentCell with all walls intact
			ArrayList<Coordinate> walledNeighbors = new ArrayList<Coordinate>();
			for (Coordinate neighbor : currentCell.getNeighbors()) {
				if (neighbor.closedCell == true) { //if you've found an unvisited neighbor
					walledNeighbors.add(neighbor);
				}
			}
			// if one or more walled neighbors found choose one at random to knock down a wall
			if (walledNeighbors.size() >= 1) {
				//Random r = new Random();
				//choose one randomly
				Coordinate next = walledNeighbors.get(myRandGen.nextInt(walledNeighbors.size()));
				// knock down the wall between it and CurrentCell
				// first have to find which side of the currentCell the neighbor
				// is on
				if (currentCell.getX() < next.getX()) { //if next is to the east of currentCell
					currentCell.removeEWall();
					next.removeWWall();
				} else if (currentCell.getX() > next.getX()) { //if next is to the west
					currentCell.removeWWall();
					next.removeEWall();
				} else if (currentCell.getY() > next.getY()) { //if next is to the north of currentCell
					currentCell.removeNWall();
					next.removeSWall();
				} else { //if next is to the south of currentCell
					currentCell.removeSWall();
					next.removeNWall();
				}

				// push CurrentCell location on the CellStack
				cellStack.push(currentCell);
				// make the new cell CurrentCell
				currentCell = next;
				// add 1 to VisitedCells
				visitedCells++;
			} // end if
			else {
				// pop the most recent cell entry off the CellStack
				// make it CurrentCell
				currentCell = cellStack.pop();

			} // end else

		}

	}

	/**
	 * displayMaze()
	 * @author Sebrianne Ferguson 
	 * displays a visual image of the maze
	 */
	public void displayMaze() {

		// always have the starting and the ending point be set
		grid[0][0].northernWall = false;
		grid[grid.length - 1][grid.length - 1].southernWall = false;

		for (int y = 0; y < grid.length; y++) {
			// horizontal lines
			for (int x = 0; x < grid.length; x++) {
				Coordinate node = grid[x][y];
				if (node.northernWall == true) { // if it has a northern wall
					if (x == grid[0].length - 1) { // add an extra plus at the
													// end
						System.out.println("+-+");
					} else {
						System.out.print("+-");
					}
				} else { // no northern wall
					if (x == grid[0].length - 1) { // add an extra plus at the
													// end
						System.out.println("+ +");
					} else {
						System.out.print("+ ");
					}
				}
			}

			// vertical lines
			for (int x = 0; x < grid.length; x++) {
				if (grid[x][y].westernWall == true) { // if it has a eatsern
														// wall
					if (x == grid[0].length - 1) { // add an extra | at the end
						if (grid[x][y].order != -1) { //if it was visited 
							System.out.printf("|%d|\n", grid[x][y].order);
						}
						else {
							System.out.println("| |");
						}
						
					} else {
						if (grid[x][y].order != -1) { //if it was visited 
							System.out.printf("|%d", grid[x][y].order);
						}
						else {
							System.out.print("| ");
						}
					}
				} else { // no eastern wall
					if (x == grid[0].length - 1) { // add an extra | at the end
						if (grid[x][y].order != -1) { //if it was visited 
							System.out.printf(" %d|\n", grid[x][y].order);
						}
						else {
							System.out.println("  |");
						}
					} else {
						if (grid[x][y].order != -1) { //if it was visited  
							System.out.printf(" %d", grid[x][y].order);
						}
						else {
							System.out.print("  ");
						}
					}
				}
			}

		}

		// now take care of the bottom border
		for (int x = 0; x < grid[0].length; x++) {
			if (grid[x][grid.length - 1].southernWall == true) { // if it has a
																	// northern
																	// wall
				if (x == grid[0].length - 1) { // add an extra plus at the end
					System.out.println("+-+");
				} else {
					System.out.print("+-");
				}
			} else { // no northern wall
				if (x == grid[0].length - 1) { // add an extra plus at the end
					System.out.println("+ +");
				} else {
					System.out.print("+ ");
				}
			}
		}
	} // end of display maze
	
	/**
	 * displayHashtag()
	 * @author Sebrianne Ferguson 
	 * displays a visual image of the maze with the hashtags instead of the numbers
	 * version only for breadth first search
	 */
	public void displayHashtag() {
		
		
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		Coordinate cord = grid[grid.length -1][grid.length -1];
		path.add(cord);
		while (cord != this.getStart()) {
			path.add(cord.parent);
			cord = cord.parent;
		}
		// always have the starting and the ending point be set
		grid[0][0].northernWall = false;
		grid[grid.length - 1][grid.length - 1].southernWall = false;

		for (int y = 0; y < grid.length; y++) {
			// horizontal lines
			for (int x = 0; x < grid.length; x++) {
				Coordinate node = grid[x][y];
				if (node.northernWall == true) { // if it has a northern wall
					if (x == grid[0].length - 1) { // add an extra plus at the
													// end
						System.out.println("+-+");
					} else {
						System.out.print("+-");
					}
				} else { // no northern wall
					if (x == grid[0].length - 1) { // add an extra plus at the
													// end
						System.out.println("+ +");
					} else {
						System.out.print("+ ");
					}
				}
			}

			// vertical lines
			for (int x = 0; x < grid.length; x++) {
				if (grid[x][y].westernWall == true) { // if it has a eatsern
														// wall
					if (x == grid[0].length - 1) { // add an extra | at the end
						if (grid[x][y].order != -1 && path.contains(grid[x][y])) { //if it was visited 
							System.out.print("|#|\n");
						}
						else {
							System.out.println("| |");
						}
						
					} else {
						if (grid[x][y].order != -1 && path.contains(grid[x][y])) { //if it was visited 
							System.out.print("|#");
						}
						else {
							System.out.print("| ");
						}
					}
				} else { // no eastern wall
					if (x == grid[0].length - 1) { // add an extra | at the end
						if (grid[x][y].order != -1 && path.contains(grid[x][y])) { //if it was visited 
							System.out.print(" #|\n");
						}
						else {
							System.out.println("  |");
						}
					} else {
						if (grid[x][y].order != -1 && path.contains(grid[x][y])) { //if it was visited  
							System.out.print(" #");
						}
						else {
							System.out.print("  ");
						}
					}
				}
			}

		}

		// now take care of the bottom border
		for (int x = 0; x < grid[0].length; x++) {
			if (grid[x][grid.length - 1].southernWall == true) { // if it has a
																	// northern
																	// wall
				if (x == grid[0].length - 1) { // add an extra plus at the end
					System.out.println("+-+");
				} else {
					System.out.print("+-");
				}
			} else { // no northern wall
				if (x == grid[0].length - 1) { // add an extra plus at the end
					System.out.println("+ +");
				} else {
					System.out.print("+ ");
				}
			}
		}
	} // end of display maze

	/**
	 * Solves the maze in BFS
	 * @param c
	 * @author Adham Kamel and Sebrianne Ferguson
	 */
	public boolean solveMazeBFS(Coordinate c) {
		Queue<Coordinate> toExplore = new LinkedList<>(); //create a queue for BFS
		Coordinate exit = grid[grid.length - 1][grid.length - 1]; //set the end point
		marked.add(c); //add to the list of visited nodes
		toExplore.add(c); //add to the linked list
		
		c.order = 0;
		int counter = 1; //for displaying all of the steps
		
		while (!toExplore.isEmpty()) { //while there is still stuff in the queue
			Coordinate current = toExplore.remove(); //dequeue the element
			
			if (current.equals(exit)){ //check to see if you've gotten to the end
				//if you have display the maze and be done
				this.displayMaze();
				return true;
			}
			
			for (Coordinate neighbor : current.getNeighbors()) {
				if (!marked.contains(neighbor)) { //if we havent gone to this coordinate already
					
					//check to see if there is actually a knocked down wall between the 2
					//north 
					if (current.northernWall == false && current.neighborType(neighbor) == Direction.NORTH) {
						if (current.getX() != 0 && current.getY() != 0) { //youre not at the starting point
							toExplore.add(neighbor); //add to the queue
							marked.add(neighbor); //add to the visited nodes list
							neighbor.order = counter % 10; //so the display isn't shifted
							neighbor.addParent(current);
							counter++;
						}
					}
					
					//next check the east
					else if (current.easternWall == false && current.neighborType(neighbor) == Direction.EAST) {
						toExplore.add(neighbor);
						marked.add(neighbor);
						neighbor.order = counter % 10;
						neighbor.addParent(current);
						counter++;
					}
					
					//next check the south
					else if (current.southernWall == false && current.neighborType(neighbor) == Direction.SOUTH) {
						toExplore.add(neighbor);
						marked.add(neighbor);
						neighbor.order = counter % 10;
						neighbor.addParent(current);
						counter++;
					}
					
					//next check the west
					else if (current.westernWall == false && current.neighborType(neighbor) == Direction.WEST) {
						toExplore.add(neighbor);
						marked.add(neighbor);
						neighbor.order = counter % 10;
						neighbor.addParent(current);
						counter++;
					}
					
				}
			}


		}
		
		
		return false; //if you can't find a path
	}

	/**
	 * Solves the maze in DFS
	 * @param c
	 * @author Adham Kamel and Sebrianne Ferguson
	 */
	public boolean solveMazeDFS(Coordinate c) {
		
		//int time = 0; //to keep track for printing
		c.order = 0;
		marked = new ArrayList<Coordinate>();
		marked.add(c);
		
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid.length; y++) {
					boolean dfs = DFS_Visit(grid[x][y]);
					if (dfs == true) {
						this.displayMaze();
						return true;
					}
				
			}
		}
		
		//if you can't find a path
		return false;
	}
	
	/**
	 * @author Adham Kamel
	 * @return the start coordinate
	 */
	public Coordinate getStart(){
		return grid[0][0];
	}
	
	/**
	 * DFS_Visit
	 * @author Sebrianne Ferguson
	 * @param c - the coordinate we're doing dfs on
	 * @param time - the number of steps you've taken so far
	 */
	public boolean DFS_Visit(Coordinate current) {
		
		Coordinate exit = grid[grid.length - 1][grid.length - 1]; //where we want to stop
		
		if (current.equals(exit)) { //let the caller method know that we've found the path
			return true;
		}

		//if we haven't gotten to the end, look for an available neighbor and dfs that neighbor
		for (Coordinate neighbor: current.neighbors) {
			if(!marked.contains(neighbor)) {
				//check to see if there is a wall down between the current node and the neighbor
				//if so, then recursively call DFS_Visit on that neighbor
				//newNeighbors++;
				if (current.northernWall == false && current.neighborType(neighbor) == Direction.NORTH) {
					time++;
					neighbor.order = time % 10;
					neighbor.addParent(current);
					marked.add(neighbor);
					if (DFS_Visit(neighbor)) {
						return true;
					}

				}
				
				//next check the east
				else if (current.easternWall == false && current.neighborType(neighbor) == Direction.EAST) {
					time++;
					neighbor.order = time % 10;
					neighbor.addParent(current);
					marked.add(neighbor);
					if (DFS_Visit(neighbor)) {
						return true;
					}

				}
				
				//next check the south
				else if (current.southernWall == false && current.neighborType(neighbor) == Direction.SOUTH) {
					time++;
					neighbor.order = time % 10;
					neighbor.addParent(current);
					marked.add(neighbor);
					if (DFS_Visit(neighbor)) {
						return true;
					}

				}
				
				//next check the west
				else if (current.westernWall == false && current.neighborType(neighbor) == Direction.WEST) {
					time++;
					neighbor.order = time % 10;
					neighbor.addParent(current);
					marked.add(neighbor);
					if (DFS_Visit(neighbor)) {
						return true;
					}

				}
			}
			

		}

		return false;
		
	}

}