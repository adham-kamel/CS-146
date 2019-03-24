package sjsu.kamel.cs146.project1;
import java.util.*;

/**
 * Setting up the node class with getters and setters to manipulate given linked list
 * @author adham_kamel
 *
 */
public class Node
{
	public int		data;	// Data in the node
	public Node		next;	// The node after a certain node
	public Node 	prev;	// The node before a certain node
	
	/**
	 * Initializes the node
	 * @param number - data we want to put in the node
	 */
	Node(int number)
	{
		next = null;
		data = number; 
		prev = null;
	}
	
	/**
	 * Gets the next node in the linked list
	 * @return next
	 */
	Node getNext()
	{
		return next;
	}
	
	/**
	 * Set the next node in the list to a given node
	 * @param next - node we want to be next in the list
	 */
	void setNext(Node next)
	{
		this.next = next;
	}
	
	/**
	 * Gets the previous node in the linked list
	 * @return prev
	 */
	Node getPrev(){
		return prev;
	}
	
	/**
	 * Set the previous node in the list to a given node
	 * @param prev - node we want to be previous in the list
	 */
	void setPrev(Node prev){
		this.prev = prev;
	}
	
	/**
	 * Gets the data inside of a node
	 * @return data
	 */
	int getData()
	{
		return data;
	}
}