package nz.ac.auckland.se281.a4.ds;

//*******************************
//YOU SHOUD MODIFY THE SPECIFIED 
//METHODS  OF THIS CLASS
//HELPER METHODS CAN BE ADDED
//REQUIRED LIBRARIES ARE ALREADY 
//IMPORTED -- DON'T ADD MORE
//THIS CLASS ALSO HAS TO BE MADE 
//GENERIC
//*******************************

/**
 * The Linked List Class Has only one head pointer to the start node (head)
 * Nodes are indexed starting from 0. The list goes from 0 to size-1.
 *
 * @author Partha Roop
 */
public class LinkedList<T> {
	// the head of the linked list
	private Node<T> head;

	/**
	 * Constructor for LinkedList
	 */
	public LinkedList() {
		head = null;
	}

	/**
	 * This method adds a node with specified data as the start node of the list
	 * TODO: Complete this method
	 *
	 * @param element a parameter, which is the value of the node to be prepended
	 */
	public void prepend(T element) {
		// create new node and set it as new head
		Node<T> n = new Node<T>(element);
		n.setNext(head);
		head = n;
	}

	/**
	 * This method adds a node with specified data as the end node of the list TODO:
	 * Complete this method
	 *
	 * @param element a parameter, which is the value of the node to be appended
	 */

	// Note this method has been refactored using the helper methods
	// I will do this as a small ACP exercise in class
	public void append(T element) {
		// creates new node
		Node<T> t = new Node<T>(element);
		if (head == null) {
			// if empty new node is head
			head = t;
			return;
		}
		Node<T> node = head;
		while (node.getNext() != null) {
			// if not empty, get next node and set to end
			node = node.getNext();
		}
		node.setNext(t);
	}

	/**
	 * This method gets the value of a node at a given position TODO: Complete this
	 * method
	 *
	 * @param pos an integer, which is the position
	 * @return the value at the position pos
	 * @throws InvalidPositionException if position is less than 0 or greater than
	 *                                  size-1
	 */
	public T get(int pos) throws InvalidPositionException {
		Node<T> node = head;
		int count = 0;
		// check if valid position
		if (pos < 0 && pos > size() - 1) {
			throw new InvalidPositionException();
		}
		while (count != pos) {
			// loop through the list to get to position
			count++;
			node = node.getNext();
		}
		return node.getValue();
	}

	/**
	 * This method adds an node at a given position in the List TODO: Complete this
	 * method
	 * 
	 * @param pos:     an integer, which is the position
	 * @param element: the element to insert
	 * @throws InvalidPositionException if position is less than 0 or greater than
	 *                                  size-1
	 */
	public void insert(int pos, T element) throws InvalidPositionException {
		// check if valid position
		if (pos < 0 && pos > size() - 1) {
			throw new InvalidPositionException();
		}
		Node<T> node = new Node<T>(element);
		// inerst at start
		if (pos == 0) {
			node.setNext(head);
			head = node;
		} else {
			Node<T> prev = head;
			// get position in list and insert the node
			for (int i = pos; i < i - 1; i++)
				prev = prev.getNext();
			Node<T> next = prev.getNext();
			node.setNext(next);
			prev.setNext(node);
		}
	}

	/**
	 * This method removes an node at a given position TODO: Complete this method
	 *
	 * @param pos: an integer, which is the position
	 */
	public void remove(int pos) throws InvalidPositionException {
		// check if valid position
		if (pos < 0 && pos > size() - 1) {
			throw new InvalidPositionException();
		}
		// set tempory node
		Node<T> tempNode = head;

		// start of list
		if (pos == 0) {
			head = tempNode.getNext();
			return;
		}
		// get position
		for (int i = 0; tempNode != null && i < pos - 1; i++) {
			tempNode = tempNode.getNext();
		}
		if (tempNode == null || tempNode.getNext() == null) {
			return;
		}
		// replace empty node position
		Node<T> nextNode = tempNode.getNext().getNext();
		tempNode.setNext(nextNode);

	}

	/**
	 * This method returns the size of the Linked list TODO: Complete this method
	 *
	 * @return the size of the list
	 */
	public int size() {
		int i = 0;
		Node<T> start = head;
		// loop thorugh list an increment a count to find size of list
		while (start != null) {
			start = start.getNext();
			i++;
		}
		return i;
	}

	/**
	 * This method is used for printing the data in the list from head till the last
	 * node
	 *
	 */
	public void print() {
		Node<T> node = head;
		while (node != null) {
			System.out.println(node);
			node = node.getNext();
		}
	}
}