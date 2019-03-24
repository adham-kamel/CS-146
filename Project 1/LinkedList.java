package sjsu.kamel.cs146.project1;

/**
 * Defines the LinkedList class with add and remove methods as well as getting the listSize
 * 
 * @author adham_kamel
 *
 */
public class LinkedList {

	public Node head; 		// the first node in the list
	public Node tail; 		// the last node in the list before it goes back to the head
	private int listSize; 	// the size of the linked list

	/**
	 * Constructor to define the head tail and list size
	 */
	LinkedList() {
		head = null;
		tail = null;
		listSize = 0;
	}

	/**
	 * Adds a node with given data
	 * @param data - the data with the node
	 */
	public void add(int data) {
		Node temp = new Node(data); 	// Buffer node to define head and tail
		if (head == null) {
			head = temp;
			tail = head;
			tail.setNext(head);
			head.setNext(tail);
		}
		Node current = head;			// Node to iterate through the linked list
		while (current.getNext() != head) {
			current = current.getNext();
		}
		current.setNext(temp);
		temp.setNext(head);
		tail = temp;
		listSize++;
	}

	/**
	 * Removes a node from the linked list
	 * @param node - node that we want to remove from the linked list
	 */
	public void remove(Node node) {
		int dataNode = node.getData();
		Node temp = head;

		if (temp != null && temp.getData() == dataNode) {
			head = temp.getNext(); // Changed head
			tail.setNext(head);
			listSize--;
			return;
		}

		while (temp != null && temp.getData() != dataNode) {
			node.setPrev(temp);
			temp = temp.getNext();
		}

		if (temp == null)
			return;

		node.getPrev().setNext(temp.getNext());
		listSize--;
	}

	/**
	 * Size of the list
	 * @return listSize
	 */
	public int size() {
		return listSize;
	}
	
	 
}
