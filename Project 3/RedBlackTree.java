package sjsu.kamel.cs146.project3;

public class RedBlackTree<Key extends Comparable<Key>> {

	private RedBlackTree.Node<String> root;

	public static class Node<Key extends Comparable<Key>> { // changed to static

		Key key;
		Node<String> parent;
		Node<String> leftChild;
		Node<String> rightChild;
		boolean isRed;
		int color;

		public Node(Key data) {
			this.key = data;
			leftChild = null;
			rightChild = null;
		}

		public int compareTo(Node<Key> n) { // this < that <0
			return key.compareTo(n.key); // this > that >0
		}

		public boolean isLeaf() {
			// if (this.equals(root) && this.leftChild == null &&
			// this.rightChild == null)
			// return true;
			// if (this.equals(root))
			// return false;
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}
	}

	public boolean isLeaf(RedBlackTree.Node<String> n) {
		/*
		 * if (n.equals(root) && n.leftChild == null && n.rightChild == null)
		 * return true; if (n.equals(root)) return false;
		 */
		if (n.leftChild == null && n.rightChild == null) {
			return true;
		}
		return false;
	}

	public interface Visitor<Key extends Comparable<Key>> {
		/**
		 * This method is called at each node.
		 * 
		 * @param n
		 *            the visited node
		 */
		void visit(Node<Key> n);
	}

	public void visit(Node<Key> n) {
		System.out.println(n.key);
	}

	public void printTree() { // preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;
		printTree(currentNode);
	}

	public void printTree(RedBlackTree.Node<String> node) {
		System.out.print(node.key);
		if (node.isLeaf()) {
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}

	// place a new node in the RB tree with data the parameter and color it red.
	public void addNode(String data) { // this < that <0. this > that >0
		RedBlackTree.Node<String> node = new RedBlackTree.Node<String>(data);
		node.isRed = true;
		node.color = 0;
		if (root == null) {
			root = node;
		} else {
			RedBlackTree.Node<String> current = root;
			while (true) {
				if (current.compareTo(node) > 0) {
					if (current.leftChild == null) {
						current.leftChild = node;
						node.parent = current;
						break;
					}
					current = current.leftChild;
				} 
				else if (current.compareTo(node) < 0) {
					if (current.rightChild == null) {
						current.rightChild = node;
						node.parent = current;
						break;
					}
					current = current.rightChild;
				}
			}
		}
		fixTree(node);
	}

	public void insert(String data) {
		addNode(data);
	}

	public RedBlackTree.Node<String> lookup(String k) {
		if (root == null) {
			return null;
		} 
		else {
			RedBlackTree.Node<String> current = root;
			while (!current.key.equals(k)) {
				if (current.key.compareTo(k) > 0) {
					if (current.leftChild == null) {
						return null;
					}
					current = current.leftChild;
				} 
				else if (current.key.compareTo(k) <= 0) {
					if (current.rightChild == null) {
						return null;
					}
					current = current.rightChild;
				}
			}
			return current;
		}
	}

	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n) {
		if (n == n.parent.leftChild) {
			if (n.parent.rightChild == null) {
				return null;
			}
			return n.parent.rightChild;
		} else {
			if (n.parent.leftChild == null) {
				return null;
			}
			return n.parent.leftChild;
		}
	}

	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n) {
		if (n.parent == root){
			return null;
		}
		if (n.parent == n.parent.parent.leftChild) {
			if (n.parent.parent.rightChild == null) {
				return null;
			}
			return n.parent.parent.rightChild;
		} else {
			if (n.parent.parent.leftChild == null) {
				return null;
			}
			return n.parent.parent.leftChild;
		}
	}

	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n) {
		if (n.parent.parent == null) {
			return null;
		}
		return n.parent.parent;
	}

	public void rotateLeft(RedBlackTree.Node<String> x) {
		RedBlackTree.Node<String> y = x.rightChild;
		x.rightChild = y.leftChild;
		if (y.leftChild != null){
			y.leftChild.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null){
			root = y;
		}
		else if(x == x.parent.leftChild){
			x.parent.leftChild = y;
		}
		else{
			x.parent.rightChild = y;
		}
		y.leftChild = x;
		x.parent = y;
	}

	public void rotateRight(RedBlackTree.Node<String> y) {
		RedBlackTree.Node<String> x = y.leftChild;
		y.leftChild = x.rightChild;
		if (x.rightChild != null){
			x.rightChild.parent = y;
		}
		x.parent = y.parent;
		if (y.parent == null){
			root = x;
		}
		else if (y == y.parent.rightChild){
			y.parent.rightChild = y;
		}
		else{
			y.parent.leftChild = y;
		}
		x.rightChild = y;
		y.parent = x;
	}

	public void fixTree(RedBlackTree.Node<String> current) {
		// when current is the root node
		if (current == root) {
			current.isRed = false;
			current.color = 1;
			return;
		}
		// Parent is black
		if (current.parent.isRed == false) {
			return;
		}
		// The current node is red and the parent node is red.
		if (current.isRed == true && current.parent.isRed == true) {
			// Aunt node is empty or black
			if (getAunt(current) == null || getAunt(current).isRed == false) {
				// Current.parent is left child of current.grandparent
				// Current is right child of current.parent
				if (current == current.parent.rightChild && 
						current.parent == getGrandparent(current).leftChild) {
					rotateLeft(current.parent);
					fixTree(current.parent);
				}
				// Current.parent is right child of current.grandparent
				// Current is left child of current.parent
				else if (current == current.parent.leftChild && 
						current.parent == getGrandparent(current).rightChild) {
					rotateRight(current.parent);
					fixTree(current.parent);
				}
				// Current.parent is left child of current.grandparent
				// Current is left child of current.parent
				else if (current == current.parent.leftChild && 
						current.parent == getGrandparent(current).leftChild) {
					current.parent.isRed = false;
					current.parent.color = 1;
					getGrandparent(current).isRed = true;
					getGrandparent(current).color = 0;
					rotateRight(getGrandparent(current));
					return;
				}
				// Current.parent is right child of current.grandparent
				// Current is right child of current.parent
				else if (current == current.parent.rightChild && 
						current.parent == getGrandparent(current).rightChild) {
					current.parent.isRed = false;
					current.parent.color = 1;
					getGrandparent(current).isRed = true;
					getGrandparent(current).color = 0;
					rotateLeft(getGrandparent(current));
					return;
				}
			}
			// Aunt node is red
			else if (getAunt(current).isRed == true) {
				current.parent.isRed = false;
				current.parent.color = 1;
				getAunt(current).isRed = false;
				getAunt(current).color = 1;
				getGrandparent(current).isRed = true;
				getGrandparent(current).color = 0;
				fixTree(getGrandparent(current));
			}
		}
	}

	public boolean isEmpty(RedBlackTree.Node<String> n) {
		if (n.key == null) {
			return true;
		}
		return false;
	}

	public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child) {
		if (child.compareTo(parent) < 0) {// child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor<String> v) {
		preOrderVisit(root, v);
	}

	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}
}
