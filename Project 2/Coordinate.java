package sjsu.kamel.cs146.project2;

import java.util.ArrayList;

public class Coordinate {
	int x; //x coordinate
	int y; //y coordinate
	boolean northernWall; 
	boolean southernWall;
	boolean easternWall;
	boolean westernWall;
	boolean closedCell;
	ArrayList<Coordinate> neighbors; //all the neighbors of the node
	ArrayList<Coordinate> walledNeighbors; //all the neighbors whose closedCell == true
	int order; //added by Sebrianne on 10/28
	Coordinate parent; 
	
	/**
	 * Coordinate() ctor
	 * @param x position
	 * @param y position
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		this.order = -1;//gonna be used to display the order in search
		this.neighbors = new ArrayList<Coordinate>();
		this.walledNeighbors = new ArrayList<Coordinate>();
		northernWall = true;
		southernWall = true;
		easternWall = true;
		westernWall = true;
		closedCell = true;
		parent = null;
	}
	
	/**
	 * getX()
	 * @return x value
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * getY()
	 * @return y value
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * addNeighbor()
	 * adds an outgoing node neighbor to this node's neighbors list.
	 * @param c
	 */
	public void addNeighbor(Coordinate c) {
		this.neighbors.add(c);
	}
	
	/**
	 * getNeighbors()
	 * @return neighbors of the node
	 */
	public ArrayList<Coordinate> getNeighbors() {
		return this.neighbors;
	}
	
	/**
	 * removeNWall()
	 * removes the northern wall
	 */
	public void removeNWall() {
		this.northernWall = false;
		this.closedCell= false;
	}
	
	/**
	 * removeSWall()
	 * removes the southern wall
	 */
	public void removeSWall() {
		this.southernWall = false;
		this.closedCell = false;
	}
	
	/**
	 * removeEWall()
	 * removes the eastern wall
	 */
	public void removeEWall() {
		this.easternWall = false;
		this.closedCell = false;
	}
	
	/**
	 * removeWWall()
	 * removes the western wall
	 */
	public void removeWWall() {
		this.westernWall = false;
		this.closedCell = false;
	}

	
	public String toString(){
		System.out.println("XY: " + this.getX() + ", " + this.getY());
		return "";
	}
	
	
	
	public Direction neighborType(Coordinate c) {
		if (c.getX() > this.getX()) {
			return Direction.EAST;
		}
		else if (c.getX() < this.getX()) {
			return Direction.WEST;
		}
		else if (c.getY() < this.getY()) {
			return Direction.NORTH;
		}
		else if (c.getY() > this.getY()) {
			return Direction.SOUTH;
		}
		return Direction.NA; //shouldnt get to this point
	}
	
	
	public void addParent(Coordinate c) {
		this.parent = c;
	}
	

}
