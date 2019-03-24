package sjsu.kamel.cs146.project1;


/**
 * Creates a circular linked list of nodes to determine which position you
 * should stand in to win your freedom if there are n prisoners
 * 
 * @author adham_kamel
 *
 */
public class PrisonerGame {

	public LinkedList prisonerList;	// list of prisoners

	/**
	 * Initializes the list of prisoners we want
	 */
	public PrisonerGame() {
		prisonerList = new LinkedList();
	}

	/**
	 * Adds all prisoners to the linked list
	 * @param n - number of prisoners in the list
	 * @return prisonerList - linked list with all the prisoners
	 */
	public LinkedList roundEmUp(int n) {
		for (int i = 1; i <= n; i++) {
			prisonerList.add(i);
		}
		return prisonerList;
	}

	/**
	 * Eliminates a set list of prisoners by k prisoners until There is one
	 * prisoner standing who will be set free
	 * 
	 * @param k - eliminating every number of k prisoners
	 * @return winner - will return the last prisoner standing
	 */
	public int eliminatePrisoner(int k) {
		Node prisoner = prisonerList.head; // First prisoner in list
		Node winner = null;
		if (prisoner != null) {
			while (prisonerList.size() != 1) {
				for (int i = 1; i <= k; i++) {
					prisoner = prisoner.getNext();
				}
				prisonerList.remove(prisoner);
				prisoner = prisoner.getNext();
			}
			winner = prisonerList.head;
		}
		return winner.getData();
	}
}
